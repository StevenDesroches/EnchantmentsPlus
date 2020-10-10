package de.geolykt.enchantments_plus.arrows.enchanted;

import de.geolykt.enchantments_plus.arrows.EnchantedArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PenetratingArrow extends EnchantedArrow {

    public PenetratingArrow(Arrow entity, int level, double power) {
        super(entity, level, power);
    }

    public boolean onImpact(EntityDamageByEntityEvent evt) {
        evt.setDamage((evt.getDamage() * (1.25 * (level + 1))));
        die();
        return true;
    }

}
