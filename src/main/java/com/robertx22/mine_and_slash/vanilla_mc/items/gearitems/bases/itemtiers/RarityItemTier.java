package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.itemtiers;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import static net.minecraft.item.ToolMaterials.IRON;
import static net.minecraft.item.ToolMaterials.WOOD;

public class RarityItemTier implements ToolMaterial {

    public RarityItemTier(int rar) {
        this.rar = Rarities.Gears.get(rar);
    }

    GearRarity rar;

    @Override
    public int getDurability() {
        return (int) (IRON.getDurability() + (WOOD.getDurability() * rar
            .itemTierPower()));
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return IRON.getMiningSpeedMultiplier() + WOOD.getMiningSpeedMultiplier() * rar
            .itemTierPower();
    }

    @Override
    public float getAttackDamage() {
        return IRON.getAttackDamage();
    }

    @Override
    public int getMiningLevel() {
        return (int) (IRON.getMiningLevel() + 1 * rar.itemTierPower());
    }

    @Override
    public int getEnchantability() {
        return (int) (IRON.getEnchantability() + (WOOD.getEnchantability() * rar
            .itemTierPower()));
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STRUCTURE_BLOCK);
    }
}
