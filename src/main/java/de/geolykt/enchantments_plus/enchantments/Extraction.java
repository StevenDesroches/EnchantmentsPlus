package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.Storage;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.enums.Tool;
import de.geolykt.enchantments_plus.util.Utilities;

import static de.geolykt.enchantments_plus.enums.Tool.PICKAXE;
import static org.bukkit.Material.*;
import static org.bukkit.entity.EntityType.EXPERIENCE_ORB;

public class Extraction extends CustomEnchantment {

    public static final int ID = 12;

    @Override
    public Builder<Extraction> defaults() {
        return new Builder<>(Extraction::new, ID)
            .maxLevel(3)
            .loreName("Extraction")
            .probability(0)
            .enchantable(new Tool[]{PICKAXE})
            .conflicting(Switch.class)
            .description("Smelts and yields more product from ores")
            .cooldown(0)
            .power(1.0)
            .handUse(Hand.LEFT)
            .base(BaseEnchantments.EXTRACTION);
    }

    @Override
    public boolean onBlockBreak(BlockBreakEvent evt, final int level, boolean usedHand) {
        if (evt.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return false;
        }
        if (evt.getBlock().getType() == GOLD_ORE || evt.getBlock().getType() == IRON_ORE) {
            Utilities.damageTool(evt.getPlayer(), 1, usedHand);
            for (int x = 0; x < Storage.rnd.nextInt((int) Math.round(power * level + 1)) + 1; x++) {
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
                    new ItemStack(evt.getBlock().getType() == GOLD_ORE ?
                        GOLD_INGOT : IRON_INGOT));
            }
            ExperienceOrb o = (ExperienceOrb) evt.getBlock().getWorld()
                                                 .spawnEntity(evt.getBlock().getLocation(), EXPERIENCE_ORB);
            o.setExperience(
                evt.getBlock().getType() == IRON_ORE ? Storage.rnd.nextInt(5) + 1 : Storage.rnd.nextInt(5) + 3);
            evt.getBlock().setType(AIR);
            Utilities.spawnParticle(evt.getBlock().getLocation(), Particle.FLAME, 10, .1f, .5f, .5f, .5f);
            return true;
        }
        return false;
    }
}
