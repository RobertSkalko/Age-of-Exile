package com.robertx22.age_of_exile.database.data.spell_schools.parser;

import com.google.common.hash.HashCode;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.Locale;

public class GridPoint {

    public int x;
    public int y;
    private String id;

    boolean isTalent = false;
    boolean isConnector = false;

    public String getId() {
        return id.toLowerCase();
    }

    public GridPoint(int x, int y, String str) {
        this.x = x;
        this.y = y;
        this.id = str;

        this.isTalent = isTalent();
        this.isConnector = isConnector();
    }

    public boolean isInDistanceOf(GridPoint other) {
        return Math.abs(x - other.x) < 10 && Math.abs(y - other.y) < 10;
    }

    public PointData getPoint() {
        return new PointData(x, y);
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

    public String getPerk() {
        return getId().toLowerCase(Locale.ROOT);
    }

    public static String CENTER_ID = "[CENTER]";

    private boolean isTalent() {
        return id.length() > 2 && !isCenter();
    }

    public boolean isCenter() {
        return id.equalsIgnoreCase(CENTER_ID);
    }

    static String CONNECTOR_ID = "o";

    private boolean isConnector() {
        return id.equalsIgnoreCase(CONNECTOR_ID);
    }

}