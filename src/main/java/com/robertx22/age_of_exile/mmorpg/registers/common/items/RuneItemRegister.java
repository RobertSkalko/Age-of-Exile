package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuneItemRegister extends BaseItemRegistrator {

    public HashMap<String, RuneItem> MAP = new HashMap<>();
    public List<RuneItem> ALL = new ArrayList<>();

    public RuneItemRegister() {

        for (RuneItem.RuneType type : RuneItem.RuneType.values()) {

            RuneItem item = new RuneItem(type);

            Registry.register(Registry.ITEM, new ResourceLocation(Ref.MODID, item.GUID()), item);

            MAP.put(type.id, item);

            ALL.add(item);

        }

    }

}
