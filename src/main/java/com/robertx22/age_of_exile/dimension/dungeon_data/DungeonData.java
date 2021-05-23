package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class DungeonData {

    @Store
    public PossibleUniques uniq = new PossibleUniques();
    @Store
    public DungeonAffixes af = new DungeonAffixes(); // affixes
    @Store
    public String mobs = "";
    @Store
    public int lv = 1;
    @Store
    public int t = 1; // tier
    @Store
    public int floor = 0; // todo delete
    @Store
    public String uuid = "";
    @Store
    public Boolean team = false;

    public boolean isEmpty() {
        return uuid.isEmpty();
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        list.add(getAffixedName());

        TooltipInfo info = new TooltipInfo();

        af.getStats(floor, lv)
            .forEach(x -> list.addAll(x.GetTooltipString(info)));

        list.add(TooltipUtils.tier(t));
        list.add(TooltipUtils.level(lv));

        return list;

    }

    public MutableText getAffixedName() {
        return af.prefix.getAffix()
            .locName()
            .append(" ")
            .append("Lair ")
            .append(af.suffix.getAffix()
                .locName());
    }

    public DungeonMobList getMobList() {
        return Database.DungeonMobLists()
            .get(mobs);
    }

}
