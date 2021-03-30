package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.item.ItemStack;

public interface ICommonDataItem<R extends Rarity> extends ITiered, ISalvagable, ITooltip, IRarity {

    DataItemType getDataType();

    @Override
    default boolean isSalvagable(SalvageContext context) {
        return true;
    }

    void saveToStack(ItemStack stack);

    static ICommonDataItem load(ItemStack stack) {

        GearItemData gear = Gear.Load(stack);
        if (gear != null) {
            return gear;
        }
        SkillGemData gem = SkillGemData.fromStack(stack);
        if (gem != null) {
            return gem;
        }

        return null;
    }

}
