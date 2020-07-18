package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases;

import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class BaseBaublesItem extends Item implements IAutoLocName {

    public int rarity = 0;

    public BaseBaublesItem(int rar) {

        super(ItemUtils.getDefaultGearProperties()
            .maxCount(1)
            .maxDamage(BaseArmorItem.GetMat(BaseArmorItem.Type.PLATE, rar)
                .getDurability(EquipmentSlot.CHEST)));
        this.rarity = rar;
    }

    @Override
    public int getEnchantability() {
        return 9 + this.rarity;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return getFormatedForLangFile(Registry.ITEM.getId(this)
            .toString());
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

}
