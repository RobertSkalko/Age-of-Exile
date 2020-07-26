package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.itemtiers;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import static net.minecraft.item.ToolMaterials.DIAMOND;
import static net.minecraft.item.ToolMaterials.IRON;

public class RarityItemTier implements ToolMaterial {

    public RarityItemTier(int rar) {
        this.rar = Rarities.Gears.get(rar);
    }

    GearRarity rar;

    @Override
    public int getDurability() {
        return DIAMOND.getDurability();
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return IRON.getMiningSpeedMultiplier();
    }

    @Override
    public float getAttackDamage() {
        return IRON.getAttackDamage();
    }

    @Override
    public int getMiningLevel() {
        return IRON.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return IRON.getEnchantability();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STRUCTURE_BLOCK);
    }
}
