package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.RarityToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.registry.Registry;

public class AoeSwordWeapon extends SwordItem implements IAutoLocName {

    public AoeSwordWeapon(WeaponTypes type) {
        super(
            new RarityToolMaterial(), 6, type.getVanillaItemAttackSpeedModifier(), (ItemUtils.getDefaultGearProperties()
                .maxDamageIfAbsent(1000)));
        this.locname = type.locName();

    }

    String locname;

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public float getAttackDamage() {
        return 6;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public int rarity = 0;

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean isEffectiveOn(BlockState blockIn) {
        return blockIn.getBlock() == Blocks.COBWEB;
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.UNUSED_PLANT && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

}
