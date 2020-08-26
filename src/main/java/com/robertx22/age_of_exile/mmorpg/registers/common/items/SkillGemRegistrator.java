package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SkillGemItem;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class SkillGemRegistrator {

    public static SkillGemRegistrator INSTANCE;

    public HashMap<String, SkillGemItem> MAP = new HashMap<>();

    public SkillGemRegistrator() {
        SlashRegistry.Spells()
            .getList()
            .forEach(x -> {
                MAP.put(x.GUID(), register(ITEM, new Identifier(Ref.MODID, "skill_gems/" + x.GUID()), new SkillGemItem(x)));
            });

    }

}
