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
    public DungeonQuestRewards quest_rew = new DungeonQuestRewards();
    @Store
    public PossibleUniques uniques = new PossibleUniques();
    @Store
    public DungeonAffixes affixes = new DungeonAffixes();
    @Store
    public String mob_list = "";
    @Store
    public int lvl = 1;
    @Store
    public int tier = 1;
    @Store
    public int floor = 0;
    @Store
    public String uuid = "";
    @Store
    public Boolean is_team = false;

    public boolean isEmpty() {
        return uuid.isEmpty();
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        list.add(getAffixedName());

        TooltipInfo info = new TooltipInfo();

        affixes.getStats(floor, lvl)
            .forEach(x -> list.addAll(x.GetTooltipString(info)));

        list.add(TooltipUtils.tier(tier));
        list.add(TooltipUtils.level(lvl));

        return list;

    }

    public MutableText getAffixedName() {
        return affixes.prefix.getAffix()
            .locName()
            .append(" ")
            .append("Lair ")
            .append(affixes.suffix.getAffix()
                .locName());
    }

    public DungeonMobList getMobList() {
        return Database.DungeonMobLists()
            .get(mob_list);
    }

}
