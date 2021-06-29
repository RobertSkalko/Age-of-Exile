package com.robertx22.age_of_exile.dimension.delve_gen;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonGridType;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.*;

@Storable
public class DelveGrid {

    @Store
    public String[][] grid = new String[10][10];

    @Store
    public int dungeons = 0;

    public static String DUNGEON = "d";
    public static String WALL = "";

    public DungeonGridType getGridType(PointData point) {

        String id = grid[point.x][point.y];

        for (DungeonGridType type : DungeonGridType.values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }

        return DungeonGridType.WALL;

    }

    public boolean isInRange(PointData point) {
        return point.x > 0 && point.y > 0 && point.x < grid.length && point.y < grid.length;
    }

    public void randomize(int maxDungeons) {
        dungeons = 0;
        //Watch watch = new Watch();

        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], WALL);
        }

        PointData start = new PointData(grid.length / 2, grid.length / 2);

        for (int i = 0; i < 15; i++) {
            if (dungeons >= maxDungeons) {
                break;
            }
            makeRandomPath(start, maxDungeons);
        }

        /*
        watch.print("Creating 2d dungeon grid ");

        for (int i = 0; i < grid.length; i++) {
            String s = "";
            for (int x = 0; x < grid.length; x++) {
                s += grid[i][x] + ",";
            }
            System.out.print(s + "\n");
        }

         */

        //System.out.print(Arrays.deepToString(grid));
    }

    private void makeRandomPath(PointData point, int maxDungeons) {

        Set<PointData> list = new HashSet<>();

        PointData current = point;

        for (int i = 0; i < grid.length * 2; i++) {
            int tries = 0;
            boolean found = false;

            while (found == false && tries < 10) {

                tries++;
                PointData dir = randomDirection(current);

                if (!isTooNearBounds(dir)) {
                    if (!list.contains(dir)) {
                        if (!formsSquare(dir)) {
                            list.add(dir);
                            found = true;
                            current = dir;
                            grid[dir.x][dir.y] = DUNGEON;
                            dungeons++;

                            if (dungeons >= maxDungeons) {
                                return;
                            }
                            if (RandomUtils.roll(20)) {
                                makePath(dir, RandomUtils.RandomRange(1, 5));
                            }
                        }
                    }
                }
            }
        }
    }

    private void makePath(PointData point, int size) {

        Set<PointData> list = new HashSet<>();

        PointData current = point;

        for (int i = 0; i < size; i++) {
            int tries = 0;
            boolean found = false;

            while (found == false && tries < 10) {

                tries++;
                PointData dir = randomDirection(current);

                if (!isTooNearBounds(dir)) {
                    if (!list.contains(dir)) {
                        if (!formsSquare(dir)) {
                            list.add(dir);
                            found = true;
                            current = dir;
                            dungeons++;
                            grid[dir.x][dir.y] = DUNGEON;
                        }
                    }
                }
            }
        }
    }

    private boolean isTooNearBounds(PointData point) {
        return point.x < 3 || point.y < 3 || point.x > grid.length - 3 || point.y > grid.length - 3;
    }

    static List<List<PointData>> checkSquares = new ArrayList<>();

    static {
        checkSquares.add(Arrays.asList(new PointData(1, 0), new PointData(0, 1), new PointData(1, 1)));
        checkSquares.add(Arrays.asList(new PointData(-1, 0), new PointData(0, -1), new PointData(-1, -1)));
        checkSquares.add(Arrays.asList(new PointData(1, 0), new PointData(0, -1), new PointData(1, -1)));
        checkSquares.add(Arrays.asList(new PointData(-1, 0), new PointData(0, 1), new PointData(-1, 1)));
    }

    private boolean formsSquare(PointData p) {

        for (List<PointData> list : checkSquares) {
            int duns = 0;
            for (PointData point : list) {
                if (grid[p.x + point.x][p.y + point.y].equals(DUNGEON)) {
                    duns++;
                }
            }
            if (duns == 3) {
                return true;
            }

        }

        return false;
    }

    private PointData randomDirection(PointData point) {
        int way = RandomUtils.roll(50) ? -1 : 1;
        if (RandomUtils.roll(50)) {
            return new PointData(point.x + way, point.y);
        } else {
            return new PointData(point.x, point.y + way);
        }
    }

}
