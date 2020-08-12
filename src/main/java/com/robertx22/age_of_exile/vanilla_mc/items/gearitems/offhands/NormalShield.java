package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.offhands;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IGearItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NormalShield extends ShieldItem implements IAutoLocName, IGearItem {

    public Identifier resource = new Identifier("");

    public NormalShield(BaseGearType slot) {

        super(properties());

        resource = getResource(slot);

    }

    static Settings properties() {

        Settings p = ItemUtils.getDefaultGearProperties();

        return p;
    }

    public static Identifier getResource(BaseGearType slot) {
        return new Identifier(Ref.MODID, "items/shields/" + slot.GUID());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameForLangFile() {
        return "Shield";
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

}