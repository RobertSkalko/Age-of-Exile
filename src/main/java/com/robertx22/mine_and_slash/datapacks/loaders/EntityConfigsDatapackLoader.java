package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

public class EntityConfigsDatapackLoader extends BaseDataPackLoader<EntityConfig> {
    static String ID = "entity_config";

    public EntityConfigsDatapackLoader() {
        super(SlashRegistryType.ENTITY_CONFIGS, ID, x -> EntityConfig.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<EntityConfig>(SlashRegistry.EntityConfigs()
            .getSerializable(), ID);
    }
}
