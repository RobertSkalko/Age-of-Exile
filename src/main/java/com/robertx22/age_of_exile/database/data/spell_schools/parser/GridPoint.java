package com.robertx22.age_of_exile.database.data.spell_schools.parser;

import com.google.common.hash.HashCode;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.Locale;

public class GridPoint {

    static int MAX_DISTANCE = 10;

    public final int x;
    public final int y;
    private final String id;

    boolean isTalent = false;
    boolean isConnector = false;
    boolean isCenter = false;

    private final PointData point;

    public String getId() {
        return id;
    }

    public GridPoint(int x, int y, String str) {
        this.x = x;
        this.y = y;
        this.id = str.toLowerCase(Locale.ROOT);

        this.point = new PointData(x, y);

        if (id.length() == 1) {
            this.isConnector = true;
        } else if (id.equalsIgnoreCase(CENTER_ID)) {
            this.isCenter = true;
        } else if (id.length() > 2) {
            this.isTalent = true;
        }

    }

    public boolean isInDistanceOf(GridPoint other) {
        return Math.abs(x - other.x) < MAX_DISTANCE && Math.abs(y - other.y) < MAX_DISTANCE;
    }

    public PointData getPoint() {
        return this.point;
    }

    @Override
    public int hashCode() {
        return HashCode.fromInt(x)
            .hashCode() + HashCode.fromInt(y)
            .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GridPoint) {
            GridPoint other = (GridPoint) obj;
            return other.x == x && other.y == y;
        }
        return false;
    }

    public static String CENTER_ID = "[CENTER]";

}