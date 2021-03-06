package de.geolykt.enchantments_plus.util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * This class has the goal of replacing the rigid CompatibilityAdapter with something more Soft-coded.<br>
 * While this leads to <strike>minimally</strike> <b>insanely</b> slower performance,
 *  is also leads to higher cross-version compatibility and flexibility with Datapacks. <br>
 *  As such, results are cached.
 */
public class RecipeUtil {

    /**
     * Returns the smelted Itemstack based on a given ItemStack.<br>
     * Does not account for stack capacity!
     */
    private static ItemStack getSmeltedVariant(ItemStack input) {
        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();
            if (!(recipe instanceof FurnaceRecipe)) {
                continue;
            }
            if (((FurnaceRecipe) recipe).getInput().getType() != input.getType()) {
                continue;
            }
            ItemStack predone = recipe.getResult();
            predone.setAmount(predone.getAmount()*input.getAmount());
            return predone;
        }
        return new ItemStack(Material.AIR);
    }
    
    /**
     * Recipe cache for smelting recipes
     */
    private static final HashMap<Material, ItemStack> smeltCache = new HashMap<>();
    
    /**
     * Returns the smelted Itemstack based on a given ItemStack.<br>
     * Does not account for stack capacity!
     * Returns air if the recipe was not found.
     * @param input The input itemstack
     * @param updateCache Whether or not the Cache should be updated
     * @return the smelted Itemstack based on a given ItemStack.
     */
    public static ItemStack getSmeltedVariant(ItemStack input, boolean updateCache) {
        if (updateCache) {
            ItemStack out = getSmeltedVariant(input);
            smeltCache.put(input.getType(), out);
            out.setAmount(out.getAmount()*input.getAmount());
            return out;
        } else {
            ItemStack out = smeltCache.getOrDefault(input.getType(), new ItemStack(Material.AIR));
            out.setAmount(out.getAmount()*input.getAmount());
            return out;
        }
    }
    
    private static final HashMap<Material, Long> cacheDuration = new HashMap<>();
    public static final int REFRESH_EVERY = 60000; // Refresh every minute
    
    /**
     * Returns the smelted Itemstack based on a given ItemStack.<br>
     * Does not account for stack capacity!
     * Returns air if the recipe was not found.
     * Also periodically refreshes the cache, in case recipes have been updated for one reason or another.
     * @param input The input itemstack
     * @return the smelted Itemstack based on a given ItemStack.
     */
    public static ItemStack getSmeltedVariantCached(ItemStack input) {
        if (System.currentTimeMillis() - cacheDuration.getOrDefault(input.getType(), Instant.EPOCH.toEpochMilli()) > REFRESH_EVERY) {
            cacheDuration.put(input.getType(), System.currentTimeMillis());
            return getSmeltedVariant(input, true);
        } else {
            return getSmeltedVariant(input, false);
        }
    }
}
