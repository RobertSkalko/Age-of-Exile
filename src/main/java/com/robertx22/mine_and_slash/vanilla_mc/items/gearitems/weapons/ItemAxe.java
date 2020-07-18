package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseWeaponItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class ItemAxe extends BaseWeaponItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

    public ItemAxe(int rar) {
        super(rar);

        this.attackSpeed = -3F;
    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Axe";

    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean isEffectiveOn(BlockState blockIn) {
        return blockIn.getBlock() == Blocks.COBWEB;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.UNUSED_PLANT && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

}
