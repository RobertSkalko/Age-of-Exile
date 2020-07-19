package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.itemtiers;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class RarityItemTier implements ToolMaterial {

    public RarityItemTier(int rar) {
        this.rar = Rarities.Gears.get(rar);
    }

    GearRarity rar;

    @Override
    public int getDurability() {
        return (int) (net.minecraft.item.ToolMaterials.IRON.getDurability() + (net.minecraft.item.ToolMaterials.WOOD.getDurability() * rar
            .itemTierPower()));
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return net.minecraft.item.ToolMaterials.IRON.getMiningSpeedMultiplier() + net.minecraft.item.ToolMaterials.WOOD.getMiningSpeedMultiplier() * rar
            .itemTierPower();
    }

    @Override
    public float getAttackDamage() {
        return net.minecraft.item.ToolMaterials.IRON.getAttackDamage() + net.minecraft.item.ToolMaterials.WOOD.getAttackDamage() * rar
            .itemTierPower();
    }

    @Override
    public int getMiningLevel() {
        return (int) (net.minecraft.item.ToolMaterials.IRON.getMiningLevel() + 1 * rar.itemTierPower());
    }

    @Override
    public int getEnchantability() {
        return (int) (net.minecraft.item.ToolMaterials.IRON.getEnchantability() + (net.minecraft.item.ToolMaterials.IRON.getEnchantability() * rar
            .itemTierPower()));
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STRUCTURE_BLOCK);
    }
}
