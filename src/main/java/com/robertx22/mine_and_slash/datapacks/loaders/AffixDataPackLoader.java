package com.robertx22.mine_and_slash.datapacks.loaders;

import com.google.gson.JsonElement;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.registry.empty_entries.EmptyAffix;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class AffixDataPackLoader extends BaseDataPackLoader<Affix> {
    static String ID = "affixes";

    public AffixDataPackLoader() {
        super(SlashRegistryType.AFFIX, ID, x -> EmptyAffix.getInstance()
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Affix>(SlashRegistry.Affixes()
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
