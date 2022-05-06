package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Storable
public class DungeonData {

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
        af.randomize(diff);
    }

    public boolean isEmpty() {
        return uuid.isEmpty();
    }

    public List<ITextComponent> getTooltip() {

        List<ITextComponent> list = new ArrayList<>();

        list.add(getAffixedName());

        af.getStats(lv)
            .forEach(x -> list.addAll(x.GetTooltipString(new RpgLevel(this.lv))));

        list.add(TooltipUtils.level(lv));

        return list;

    }

    public IFormattableTextComponent getAffixedName() {
        return new StringTextComponent("Dungeon");
    }

    public DungeonMobList getMobList() {
        return ExileDB.DungeonMobLists()
            .get(m);
    }

}
