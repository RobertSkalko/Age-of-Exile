package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum TeamSize {
    SOLO(Words.Solo, 1, 1, 1F, 0.75F, new TeamSizeMobStrength(200, 100, 50)),
    DUO(Words.Duo, 2, 2, 2F, 1.25f, new TeamSizeMobStrength(400, 200, 100)),
    TEAM(Words.Team, 3, 10, 4F, 2F, new TeamSizeMobStrength(500, 300, 200));

    public Words word;

    public int requiredMemberAmount;
    public int maxMembers;
    public float lootMulti;
    public float expMulti;

    public TeamSizeMobStrength teamSizeMobStrength;

    TeamSize(Words word, int requiredMemberAmount, int maxMembers, float lootMulti, float xpmulti, TeamSizeMobStrength teamSizeMobStrength) {
        this.word = word;
        this.expMulti = xpmulti;
        this.requiredMemberAmount = requiredMemberAmount;
        this.maxMembers = maxMembers;
        this.lootMulti = lootMulti;
        this.teamSizeMobStrength = teamSizeMobStrength;
    }
}
