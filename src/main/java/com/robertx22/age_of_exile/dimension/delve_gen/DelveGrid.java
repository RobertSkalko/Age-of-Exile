package com.robertx22.age_of_exile.dimension.delve_gen;

import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.*;

public class DelveGrid {

    public String[][] grid = new String[50][50];

    static String DUNGEON = "d";
    static String WALL = "";

    public boolean isDungeon(PointData point) {
        return grid[point.x][point.y].equals(DUNGEON);
    }

    public void randomize() {
        Watch watch = new Watch();
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], WALL);
        }

        PointData start = new PointData(grid.length / 2, grid.length / 2);

        for (int i = 0; i < 20; i++) {
            makeRandomPath(start);
        }

        watch.print("Creating 2d dungeon grid ");

        for (int i = 0; i < grid.length; i++) {
            String s = "";
            for (int x = 0; x < grid.length; x++) {
                s += grid[i][x] + ",";
            }
            System.out.print(s + "\n");
        }

        //System.out.print(Arrays.deepToString(grid));
    }

    private void makeRandomPath(PointData point) {

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

                            if (RandomUtils.roll(10)) {
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
                            grid[dir.x][dir.y] = DUNGEON;
                        }
                    }
                }
            }
        }
    }

    boolean isTooNearBounds(PointData point) {
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
