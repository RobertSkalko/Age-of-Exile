package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases;

import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorType;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ModArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.item.ArmorItem;

public class BaseArmorItem extends ArmorItem implements IAutoLocName {

    String locname;
    boolean isUnique;

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
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

    public BaseArmorItem(ArmorTier tier, ArmorType type, String locname, EquipmentSlotType slot, boolean isunique) {
        super(new ModArmorMaterial(tier, type, isunique), slot, ItemUtils.getDefaultGearProperties());
        this.isUnique = isunique;
        this.locname = locname;
    }

    @Override
    public boolean shouldRegisterLangName() {
        return !this.isUnique;
    }

}