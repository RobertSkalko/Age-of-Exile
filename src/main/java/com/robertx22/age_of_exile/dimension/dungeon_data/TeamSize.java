package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum TeamSize {
    SOLO(Words.Solo, 1, 1, 0.75F, 0.5F, new TeamSizeMobStrength(200, 100, 50), new TeamSizeMobStrength(100, 125, 0), 1F),
    DUO(Words.Duo, 2, 2, 1.5F, 1F, new TeamSizeMobStrength(400, 200, 150), new TeamSizeMobStrength(150, 150, 50), 1.25F),
    TEAM(Words.Team, 3, 10, 3F, 1.5F, new TeamSizeMobStrength(500, 300, 200), new TeamSizeMobStrength(300, 250, 100), 1.5F);

    public Words word;

    public int requiredMemberAmount;
    public int maxMembers;
    public float lootMulti;
    public float expMulti;
    public float deathsAllowedMulti;

    TeamSizeMobStrength teamSizeMobStrength;
    TeamSizeMobStrength riftMobStrength;

    public TeamSizeMobStrength getMobStr(DungeonType type) {
        if (type == DungeonType.DUNGEON) {
            return teamSizeMobStrength;
        } else {
            return riftMobStrength;
        }
    }

    TeamSize(Words word, int requiredMemberAmount, int maxMembers, float lootMulti, float xpmulti, TeamSizeMobStrength teamSizeMobStrength, TeamSizeMobStrength riftmob, float deathsAllowedMulti) {
        this.word = word;
        this.expMulti = xpmulti;
        this.requiredMemberAmount = requiredMemberAmount;
        this.maxMembers = maxMembers;
        this.deathsAllowedMulti = deathsAllowedMulti;
        this.lootMulti = lootMulti;
        this.riftMobStrength = riftmob;
        this.teamSizeMobStrength = teamSizeMobStrength;
    }
}
