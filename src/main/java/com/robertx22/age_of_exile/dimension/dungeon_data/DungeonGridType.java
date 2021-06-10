package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.dimension.delve_gen.DelveGrid;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.util.Identifier;

public enum DungeonGridType {

    DUNGEON(DelveGrid.DUNGEON, Ref.guiId("dungeon/dungeon"), true),
    WALL(DelveGrid.WALL, Ref.guiId("dungeon/wall"), false),
    BORDER("none", Ref.guiId("dungeon/border"), false);

    public Identifier icon;
    public String id;
    public boolean hide = false;

    public boolean isDungeon() {
        return this == DUNGEON;
    }

    public boolean isBorder() {
        return this == BORDER;
    }

    DungeonGridType(String id, Identifier icon, boolean hide) {
        this.icon = icon;
        this.id = id;
        this.hide = hide;
    }
}
