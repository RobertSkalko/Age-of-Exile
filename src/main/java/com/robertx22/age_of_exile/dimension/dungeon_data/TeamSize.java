package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum TeamSize {
    SOLO(Words.Solo, 1, 1, 0.75F, 0.5F, new TeamSizeMobStrength(200, 100, 50), 1F),
    DUO(Words.Duo, 2, 2, 1.5F, 1F, new TeamSizeMobStrength(400, 200, 150), 1.25F),
    TEAM(Words.Team, 3, 10, 3F, 1.5F, new TeamSizeMobStrength(500, 300, 200), 1.5F);

    public Words word;

    public int requiredMemberAmount;
    public int maxMembers;
    public float lootMulti;
    public float expMulti;
    public float deathsAllowedMulti;

    public TeamSizeMobStrength teamSizeMobStrength;

    TeamSize(Words word, int requiredMemberAmount, int maxMembers, float lootMulti, float xpmulti, TeamSizeMobStrength teamSizeMobStrength, float deathsAllowedMulti) {
        this.word = word;
        this.expMulti = xpmulti;
        this.requiredMemberAmount = requiredMemberAmount;
        this.maxMembers = maxMembers;
        this.deathsAllowedMulti = deathsAllowedMulti;
        this.lootMulti = lootMulti;
        this.teamSizeMobStrength = teamSizeMobStrength;
    }
}
