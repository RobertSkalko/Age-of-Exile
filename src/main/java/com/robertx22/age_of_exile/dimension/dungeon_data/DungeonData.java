package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Storable
public class DungeonData {

    @Store
    public PossibleUniques u = new PossibleUniques(); // uniques
    @Store
    public DungeonAffixes af = new DungeonAffixes(); // affixes
    @Store
    private String m = ""; // mobs
    @Store
    public int lv = 1;
    @Store
    public int t = 1; // tier
    @Store
    public String uuid = "";
    @Store
    public TeamSize team = TeamSize.SOLO;

    public void setMobList(String mobs) {
        this.m = mobs;
    }

    public void randomize(int lvl, int tier) {
        lv = lvl;
        t = tier;
        m = ExileDB.DungeonMobLists()
            .random()
            .GUID();
        uuid = UUID.randomUUID()
            .toString();
        u.randomize(tier);
        af.randomize(tier);
    }

    public boolean isEmpty() {
        return uuid.isEmpty();
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        list.add(getAffixedName());

        TooltipInfo info = new TooltipInfo();

        af.getStats(lv)
            .forEach(x -> list.addAll(x.GetTooltipString(info)));

        // list.add(TooltipUtils.tier(t));
        list.add(TooltipUtils.level(lv));

        return list;

    }

    public MutableText getAffixedName() {
        return new LiteralText("Dungeon");
    }

    public DungeonMobList getMobList() {
        return ExileDB.DungeonMobLists()
            .get(m);
    }

}
