package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.dimension.delve_gen.DelveGrid;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.saveclasses.PointData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
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
