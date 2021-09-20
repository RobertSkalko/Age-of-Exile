package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class ModArmorMaterial implements IArmorMaterial {

    ArmorTier tier;
    ArmorType type;
    boolean isUnique;

    public ModArmorMaterial(ArmorTier tier, ArmorType type, boolean isUnique) {
        this.tier = tier;
        this.type = type;
        this.isUnique = isUnique;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotIn) {
        return 100 + (int) (this.tier.vanillaMat.getDurabilityForSlot(slotIn) * getExtraMulti());
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {
        return tier.vanillaMat.getDefenseForSlot(slotIn);
    }

    @Override
    public int getEnchantmentValue() {
        return (int) (tier.vanillaMat.getEnchantmentValue() * getExtraMulti());
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
    public static RegObj<SoundEvent> getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.STRUCTURE_BLOCK); // as in, nothing besides creative items should repair it
    }

}
