package de.geolykt.enchantments_plus.enchantments;

import static de.geolykt.enchantments_plus.enums.Tool.ROD;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.enums.Tool;

public class LongCast extends CustomEnchantment {

    public static final int ID = 33;

    @Override
    public Builder<LongCast> defaults() {
        return new Builder<>(LongCast::new, ID)
            .maxLevel(2)
            .loreName("Long Cast")
            .probability(0)
            .enchantable(new Tool[]{ROD})
            .conflicting(ShortCast.class)
            .description("Launches fishing hooks farther out when casting")
            .cooldown(0)
            .power(1.0)
            .handUse(Hand.RIGHT)
            .base(BaseEnchantments.LONG_CAST);
    }

    @Override
    public boolean onProjectileLaunch(ProjectileLaunchEvent evt, int level, boolean usedHand) {
        if (evt.getEntity().getType() == EntityType.FISHING_HOOK) {
            evt.getEntity().setVelocity(
                evt.getEntity().getVelocity().normalize().multiply(Math.min(1.9 + (power * level - 1.2), 2.7)));
        }
        return true;
    }
}
