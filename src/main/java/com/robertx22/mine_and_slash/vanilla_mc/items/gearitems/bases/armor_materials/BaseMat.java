package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.armor_materials;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public abstract class BaseMat implements ArmorMaterial {

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STRUCTURE_BLOCK); // as in, nothing besides creative items should repair it
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
