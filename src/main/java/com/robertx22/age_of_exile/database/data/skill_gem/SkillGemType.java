package com.robertx22.age_of_exile.database.data.skill_gem;

public enum SkillGemType {
    SKILL_GEM("skill"), SUPPORT_GEM("support");

    public String id;

    SkillGemType(String id) {
        this.id = id;
    }
}