package com.robertx22.mine_and_slash.data_generation;

import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import net.minecraft.data.DataGenerator;

public class EntityConfigsDatapackManager extends BaseDataPackManager<EntityConfig> {
    static String ID = "entity_config";

    public EntityConfigsDatapackManager() {
        super(SlashRegistryType.ENTITY_CONFIGS, ID, x -> EntityConfig.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDataProvider getDataPackCreator(DataGenerator gen) {
        return new SlashDataProvider<EntityConfig>(gen, SlashRegistry.EntityConfigs()
            .getSerializable(), ID);
    }
}
