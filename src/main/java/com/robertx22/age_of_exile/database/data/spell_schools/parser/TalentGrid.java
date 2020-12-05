package com.robertx22.age_of_exile.database.data.spell_schools.parser;

import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.uncommon.testing.Watch;

import java.util.*;

public class TalentGrid {

    SpellSchool school;

    List<List<GridPoint>> grid = new ArrayList<>();

    public GridPoint get(int x, int y) {
        return grid.get(x)
            .get(y);
    }

    public TalentGrid(SpellSchool school, String str) {
        this.school = school;

        int y = 0;
        for (String line : str.split("\n")) {
            int x = 0;
            for (String s : line.split(",")) {

                if (grid.size() <= x) {
                    grid.add(new ArrayList<>());
                }

                grid.get(x)
                    .add(new GridPoint(x, y, s));

                x++;

            }

            y++;
        }
    }

    public void loadIntoTree() {

        List<GridPoint> perks = new ArrayList<>();

        Watch gridwatch = new Watch().min(500);
        for (List<GridPoint> list : grid) {
            for (GridPoint point : list) {
                if (point.isTalent) {
                    school.calcData.addPerk(point.getPoint(), point.getId());
                    perks.add(point);
                } else if (point.isCenter) {
                    school.calcData.center = point.getPoint();
                }
            }

        }

        gridwatch.print(" Setting up talent grid ");

        Objects.requireNonNull(school.calcData.center, "Tree needs a center!");

        Watch fast2 = new Watch().min(1);
        // this can take a long time if the "hasPath" checks aren't minimized
        perks
            .forEach(one -> {
                Set<String> connectorTypes = getConnectorTypes(one);
                perks
                    .forEach(two -> {
                        if (!school.calcData.isConnected(one.getPoint(), two.getPoint())) {
                            if (one.isInDistanceOf(two)) {
                                if (hasPath(one, two, connectorTypes)) {
                                    this.school.calcData.addConnection(one.getPoint(), two.getPoint());
                                }
                            }
                        }
                    });

            });

        fast2.print(" Connecting talent tree ");

    }

    // this is very resource intensive
    private boolean hasPath(GridPoint start, GridPoint end, Set<String> connectorTypes) {

        for (String connector : connectorTypes) {

            Queue<GridPoint> openSet = new ArrayDeque<>();
            openSet.add(start);

            Set<GridPoint> closedSet = new HashSet<>();

            while (!openSet.isEmpty()) {
                GridPoint current = openSet.poll();

                if (!closedSet.add(current)) {
                    continue; // we already visited it
                }
                if (current.equals(end)) {
                    return true;
                }
                if (current.isTalent && current != start) {
                    continue; // skip exploring this path
                }

                openSet.addAll(getEligibleSurroundingPoints(current, connector));
            }

        }

        return false;
    }

    Set<String> getConnectorTypes(GridPoint p) {
        Set<String> set = new HashSet<>();

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                GridPoint c = get(p.x + x, p.y + y);
                if (c.isConnector) {
                    set.add(c.getId());
                }
            }
        }
        return set;

    }

    public Set<GridPoint> getEligibleSurroundingPoints(GridPoint p, String connector) {

        Set<GridPoint> set = new HashSet<>();
        int x = p.x;
        int y = p.y;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                GridPoint point = get(x + dx, y + dy);

                if (Math.abs(dx) == 1 && Math.abs(dy) == 1) { // we are discovering a diagonal
                    if (get(x + dx, y).isTalent || get(x, y + dy).isTalent) {
                        continue; // skip this diagonal, it crosses a talent
                    }
                }

                if (point.isTalent) {
                    set.add(point);
                } else if (point.isConnector) {
                    if (point.getId()
                        .equals(connector)) {
                        set.add(point);
                    }
                }

            }
        }

        return set;
    }

}