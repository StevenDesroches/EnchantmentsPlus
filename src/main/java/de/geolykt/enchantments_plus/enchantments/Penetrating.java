package de.geolykt.enchantments_plus.enchantments;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.arrows.EnchantedArrow;
import de.geolykt.enchantments_plus.arrows.enchanted.BlizzardArrow;
import de.geolykt.enchantments_plus.arrows.enchanted.PenetratingArrow;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.enums.Tool;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import static de.geolykt.enchantments_plus.enums.Tool.BOW;
import static de.geolykt.enchantments_plus.enums.Tool.CROSSBOW;

public class Penetrating extends CustomEnchantment {

    public static final int ID = 84;

    @Override
    public Builder<Penetrating> defaults() {
        return new Builder<>(Penetrating::new, ID)
            .maxLevel(5)
            .loreName("Penetrating")
            .probability(0)
            .enchantable(new Tool[]{CROSSBOW})
            .conflicting(Firestorm.class)
            .description("Increase damage of crossbow")
            .cooldown(0)
            .power(1.0)
            .handUse(Hand.RIGHT)
            .base(BaseEnchantments.PENETRATING);
    }

    @Override
    public boolean onEntityShootBow(EntityShootBowEvent evt, int level, boolean usedHand) {
        PenetratingArrow arrow = new PenetratingArrow((Arrow) evt.getProjectile(), level, power);
        EnchantedArrow.putArrow((Arrow) evt.getProjectile(), arrow, (Player) evt.getEntity());
        return true;
    }
}
