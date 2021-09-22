package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.CraftToolItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class CraftToolItems {

    public static void init() {

    }

    public static RegObj<CraftToolItem> COOKING = Def.item(() -> new CraftToolItem(PlayerSkillEnum.COOKING));
    public static RegObj<CraftToolItem> ALCHEMY = Def.item(() -> new CraftToolItem(PlayerSkillEnum.ALCHEMY));
    public static RegObj<CraftToolItem> INSCRIBING = Def.item(() -> new CraftToolItem(PlayerSkillEnum.INSCRIBING));

}
