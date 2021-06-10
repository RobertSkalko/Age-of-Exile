package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.dimension.delve_gen.DelveGrid;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Storable
public class MapsData {

    @Store
    public DelveGrid grid = new DelveGrid();

    @Store
    public BlockPos tel_pos = new BlockPos(0, 0, 0);

    @Store
    public PointData point_pos = new PointData(20, 20);
    @Store
    public PointData start_pos = new PointData(20, 20);

    @Store
    public HashMap<PointData, DungeonData> dungeon_datas = new HashMap<>();

    @Store
    public List<PointData> completed = new ArrayList<>();
    @Store
    public List<PointData> started = new ArrayList<>();

    @Store
    public boolean isEmpty = true;

    @Store
    public String orig_gamemode = "";

    public int distance(PointData one, PointData two) {
        if (one.equals(two)) {
            return 0;
        } else {
            return one.distanceTo(two); // todo this should use a pathfinding algo for 100% correct distance.
        }
    }

    public ItemStack getStartCostOf(PointData point) {
        int cost = 3;

        SkillItemTier tier = LevelUtils.levelToTier(dungeon_datas.get(point).lv);

        Item item = ModRegistry.TIERED.DIMENSIONAL_SHARD_MAP.get(tier);

        cost += distance(this.point_pos, point);

        return new ItemStack(item, cost);

    }

    public boolean playerCanSee(PointData point) {

        if (point.equals(start_pos)) {
            return true;
        }

        if (completed.contains(point)) {
            return true;
        } else {
            return completed.stream()
                .anyMatch(x -> x.isInDungeonRangeOf(point));
        }

    }

}
