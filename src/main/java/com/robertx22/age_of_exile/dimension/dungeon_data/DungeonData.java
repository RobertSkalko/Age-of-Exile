package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
    public int deaths = 0;
    @Store
    public String uuid = "";
    @Store
    public TeamSize team = TeamSize.SOLO;
    @Store
    public String key_item = "";
    @Store
    public String diff = "";
    @Store
    public boolean fail = false;
    @Store
    public int fail_counter = 0;
    @Store
    public DungeonType dun_type = DungeonType.DUNGEON;

    public boolean failedOrEmpty() {
        return fail || isEmpty();
    }

    public Difficulty getDifficulty() {
        return ExileDB.Difficulties()
            .get(diff);
    }

    public void setMobList(String mobs) {
        this.m = mobs;
    }

    public void randomize(int lvl, Difficulty diff) {
        lv = lvl;
        this.diff = diff.GUID();
        m = ExileDB.DungeonMobLists()
            .random()
            .GUID();
        uuid = UUID.randomUUID()
            .toString();
        u.randomize(diff);
        af.randomize(diff);
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

    public DungeonKeyItem getKeyItem() {
        try {
            return (DungeonKeyItem) Registry.ITEM.get(new Identifier(key_item));
        } catch (Exception e) {
            return null;
        }
    }
}
