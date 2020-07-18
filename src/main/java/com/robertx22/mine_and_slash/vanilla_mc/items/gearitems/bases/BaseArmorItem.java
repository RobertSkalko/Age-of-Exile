package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.armor_materials.*;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public abstract class BaseArmorItem extends ArmorItem implements IAutoLocName, IGearItem {

    public enum Type {
        CLOTH,
        LEATHER,
        PLATE
    }

    public int rarity = 0;

    @Override
    public String locNameLangFileGUID() {
        return this.getRegistryName()
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    public BaseArmorItem(Type type, int rarity, EquipmentSlot slot) {

        super(GetMat(type, rarity), slot, ItemUtils.getDefaultGearProperties());
        this.rarity = rarity;
    }

    public static BaseMat GetMat(Type type, int rarity) {

        if (rarity == IRarity.Unique) {
            return new UniqueMat();
        }

        if (type.equals(Type.PLATE)) {
            return new PlateMat();

        }
        if (type.equals(Type.CLOTH)) {
            return new ClothMat();

        }
        if (type.equals(Type.LEATHER)) {
            return new LeatherMat();

        }

        return null;

    }

}