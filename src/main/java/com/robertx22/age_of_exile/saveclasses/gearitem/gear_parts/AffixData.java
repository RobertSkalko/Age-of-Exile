package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class AffixData implements IRerollable {

    @Store
    public Integer perc = 0;

    @Store
    public String affix;

    @Store
    public Affix.Type type;

    public AffixData(Affix.Type type) {
        this.type = type;
    }

    @Factory
    private AffixData() {
    }

    public List<StatModifierInfo> getStatModInfo(GearItemData gear) {
        List<StatModifierInfo> list = new ArrayList<>();
        for (StatModifier mod : this.BaseAffix()
            .getStats()) {
            list.add(new StatModifierInfo(mod, new StatPercent(perc), gear.getLevel()));
        }
        return list;
    }

    public boolean isEmpty() {
        return perc < 1;
    }

    public Affix.Type getAffixType() {
        return type;
    }

    public Affix getAffix() {
        return ExileDB.Affixes()
            .get(this.affix);
    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        perc = RandomUtils.RandomRange(0, 100);
    }

    public final Affix BaseAffix() {
        return ExileDB.Affixes()
            .get(affix);
    }

    public boolean isValid() {
        if (!ExileDB.Affixes()
            .isRegistered(this.affix)) {
            return false;
        }
        if (this.isEmpty()) {
            return false;
        }

        return true;
    }

    public void create(GearItemData gear, Affix suffix) {
        affix = suffix.GUID();
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = null;
        try {

            FilterListWrap<Affix> list = ExileDB.Affixes()
                .getFilterWrapped(x -> x.type == getAffixType() && gear.canGetAffix(x));

            if (list.list.isEmpty()) {
                System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.type.name());
            }

            affix = list
                .random();
        } catch (Exception e) {
            System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.type.name());
            e.printStackTrace();
        }

        this.create(gear, affix);

    }
}
