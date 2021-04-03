package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum PlayerAction {

    MELEE_ATTACK(Words.Attack), BLOCK(Words.Block), NOPE(Words.None);

    public Words word;

    PlayerAction(Words word) {
        this.word = word;
    }
}
