package com.robertx22.age_of_exile.database.data.crafting_req;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class CraftingReq implements JsonExileRegistry<CraftingReq>, IAutoGson<CraftingReq> {

    public static CraftingReq SERIALIZER = new CraftingReq();

    public String item_id = "";
    public String skill = "";
    public int lvl = 1;

    public static CraftingReq of(Item item, PlayerSkillEnum skill, int lvl) {
        CraftingReq r = new CraftingReq();
        r.item_id = Registry.ITEM.getId(item)
            .toString();
        r.lvl = lvl;
        r.skill = skill.id;

        r.addToSerializables();
        return r;
    }

    public boolean meets(PlayerEntity player) {
        return Load.playerRPGData(player).professions
            .getDataFor(skill)
            .getLvl() >= lvl;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<CraftingReq> getClassForSerialization() {
        return CraftingReq.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CRAFTING_REQ;
    }

    @Override
    public String GUID() {
        return item_id;
    }
}
