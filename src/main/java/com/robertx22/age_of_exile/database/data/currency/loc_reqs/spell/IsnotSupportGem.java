package com.robertx22.age_of_exile.database.data.currency.loc_reqs.spell;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class IsnotSupportGem extends BaseLocRequirement {
    @Override
    public MutableText getText() {
        return new LiteralText("Is not a support gem");
    }

    @Override
    public boolean isAllowed(LocReqContext ctx) {

        if (ctx.data instanceof SkillGemData) {
            SkillGemData data = (SkillGemData) ctx.data;
            return data.getSkillGem().type != SkillGemType.SUPPORT_GEM;
        }

        return false;
    }
}
