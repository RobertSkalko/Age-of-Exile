package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases;

import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.armor_materials.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.registry.Registry;

public abstract class BaseArmorItem extends ArmorItem implements IAutoLocName, IGearItem {

    public enum Type {
        CLOTH,
        LEATHER,
        PLATE
    }

    String locname;

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    public BaseArmorItem(Type type, String locname, EquipmentSlot slot, boolean isunique) {

        super(GetMat(type, isunique), slot, ItemUtils.getDefaultGearProperties());
        this.locname = locname;
    }

    public static BaseMat GetMat(Type type, boolean isunique) {

        if (isunique) {
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