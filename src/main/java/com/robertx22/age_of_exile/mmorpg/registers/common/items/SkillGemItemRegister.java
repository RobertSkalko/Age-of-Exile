package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.vanilla_mc.items.SkillGemItem;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;

public class SkillGemItemRegister extends BaseItemRegistrator {

    public HashMap<ImmutablePair<StatAttribute, SkillGemType>, SkillGemItem> MAP = new HashMap();

    public SkillGemItemRegister() {

        for (StatAttribute attri : StatAttribute.values()) {

            for (SkillGemType type : SkillGemType.values()) {
                SkillGemItem item = item(new SkillGemItem(attri, type), "skill_gems/" + attri.id + "_" + type.id);
                MAP.put(ImmutablePair.of(attri, type), item);
            }
        }

    }

}
