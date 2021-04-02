package com.robertx22.age_of_exile.player_skills.items.fishing;

import com.robertx22.age_of_exile.database.data.player_skills.SkillDropTable;

public enum LureType {

    FISH("fish", "Fish") {
        @Override
        public boolean worksOn(SkillDropTable table) {
            return table.tag.equals(SkillDropTable.FISH_TAG);
        }
    }, INK("ink", "Ink") {
        @Override
        public boolean worksOn(SkillDropTable table) {
            return table.tag.equals(SkillDropTable.INK_TAG);
        }
    };

    public String id;
    public String name;

    LureType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract boolean worksOn(SkillDropTable table);
}
