package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModArmorMaterial implements ArmorMaterial {

    ArmorTier tier;
    ArmorType type;
    boolean isUnique;

    public ModArmorMaterial(ArmorTier tier, ArmorType type, boolean isUnique) {
        this.tier = tier;
        this.type = type;
        this.isUnique = isUnique;
    }

    @Override
    public int getDurability(EquipmentSlot slotIn) {
        return 100 + (int) (this.tier.vanillaMat.getDurability(slotIn) * getExtraMulti());
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slotIn) {
        return tier.vanillaMat.getProtectionAmount(slotIn);
    }

    @Override
    public int getEnchantability() {
        return (int) (tier.vanillaMat.getEnchantability() * getExtraMulti());
    }

    @Override
    public float getToughness() {
        return tier.vanillaMat.getToughness();
    }

    @Override
    public float getKnockbackResistance() {
        return tier.vanillaMat.getKnockbackResistance();
    }

    @Override
    public String getName() {
        return type.id + "/" + tier.tier;
    }

    private float getExtraMulti() {
        return isUnique ? 1.2F : 1F;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.STRUCTURE_BLOCK); // as in, nothing besides creative items should repair it
    }

}
