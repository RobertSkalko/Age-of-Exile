package com.robertx22.age_of_exile.database.data.currency.loc_reqs;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.GearItemEnum;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.text.MutableText;

public class GearEnumLocReq extends BaseLocRequirement {

    public static final GearEnumLocReq AFFIXES = new GearEnumLocReq(x -> x.canGetAffixes());
    public static final GearEnumLocReq REROLL_NUMBERS = new GearEnumLocReq(x -> x.canRerollNumbers());

    private GearEnumLocReq(Predicate<GearItemEnum> pred) {
        this.gearsThatCanDoThis = pred;
    }

    Predicate<GearItemEnum> gearsThatCanDoThis;

    @Override
    public MutableText getText() {

        MutableText comp = Words.AllowedOn.locName()
            .append(": ");

        List<GearItemEnum> enums = Arrays.stream(GearItemEnum.values())
            .filter(x -> gearsThatCanDoThis.test(x))
            .collect(Collectors.toList());

        int count = 1;
        for (GearItemEnum x : enums) {

            comp.append(x.word()
                .locName());

            if (count < enums.size()) {
                comp.append(", ");
            }

            count++;
        }

        return comp;
    }

    @Override

    public boolean isAllowed(LocReqContext context) {

        if (context.data instanceof GearItemData) {
            GearItemData gear = (GearItemData) context.data;

            GearItemEnum genum = gear.getGearEnum();

            if (gearsThatCanDoThis.test(genum)) {
                return true;
            }
        }

        return false;
    }

}
