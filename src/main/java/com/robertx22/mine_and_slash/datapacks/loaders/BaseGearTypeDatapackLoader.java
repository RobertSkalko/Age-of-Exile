package com.robertx22.mine_and_slash.datapacks.loaders;

import com.google.gson.JsonElement;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.mine_and_slash.datapacks.seriazables.SerializableBaseGearType;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class BaseGearTypeDatapackLoader extends BaseDataPackLoader<BaseGearType> {
    static String ID = "base_gear_types";

    public BaseGearTypeDatapackLoader() {
        super(SlashRegistryType.GEAR_TYPE, ID, x -> SerializableBaseGearType.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<BaseGearType>(SlashRegistry.GearTypes()
            .getSerializable(), ID);
    }

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, Profiler profiler) {

        if (MMORPG.RUN_DEV_TOOLS) {
            this.getDataPackGenerator()
                .run();
        }
        return super.prepare(resourceManager, profiler);

    }
}
