package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.items.CraftToolItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class CraftToolItems {

    public static void init() {

    }

    public static RegObj<CraftToolItem> COOKING = Def.item(() -> new CraftToolItem("Cook's Pot", PlayerSkillEnum.COOKING));
    public static RegObj<CraftToolItem> ALCHEMY = Def.item(() -> new CraftToolItem("Alchemist's Mortar", PlayerSkillEnum.ALCHEMY));
    public static RegObj<CraftToolItem> INSCRIBING = Def.item(() -> new CraftToolItem("Scribe's Fabric", PlayerSkillEnum.INSCRIBING));
    public static RegObj<CraftToolItem> ARMOR = Def.item(() -> new CraftToolItem("Armorer's Cloth", PlayerSkillEnum.ARMOR_CRAFTING));
    public static RegObj<CraftToolItem> WEAPON = Def.item(() -> new CraftToolItem("Weapon Crafter's Hammer", PlayerSkillEnum.WEAPON_CRAFTING));
    public static RegObj<CraftToolItem> JEWEL = Def.item(() -> new CraftToolItem("Jeweler's Tool", PlayerSkillEnum.JEWEL_CRAFTING));

}
