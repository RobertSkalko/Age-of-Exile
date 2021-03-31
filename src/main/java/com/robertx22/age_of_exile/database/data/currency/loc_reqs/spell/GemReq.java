package com.robertx22.age_of_exile.database.data.currency.loc_reqs.spell;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.text.MutableText;

public class GemReq extends BaseLocRequirement {

    public static GemReq INSTANCE = new GemReq();

    @Override
    public MutableText getText() {
        return Words.MustBeSpellGem.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext context) {
        return context.data instanceof SkillGemData;
    }
}
