package com.robertx22.age_of_exile.player_skills.crafting_inv;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public class ProfCraftResult {

    public boolean canCraft;
    public Words reason;

    public static ProfCraftResult success() {
        ProfCraftResult r = new ProfCraftResult();
        r.canCraft = true;
        r.reason = Words.EmptyString;
        return r;
    }

    public static ProfCraftResult fail(Words reason) {
        ProfCraftResult r = new ProfCraftResult();
        r.canCraft = false;
        r.reason = reason;
        return r;
    }
}
