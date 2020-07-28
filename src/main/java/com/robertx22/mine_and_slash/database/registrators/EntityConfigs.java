package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class EntityConfigs implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new EntityConfig(EntityTypeUtils.EntityType.MOB.name()
            .toLowerCase(Locale.ROOT), 1).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityType.ANIMAL.name()
            .toLowerCase(Locale.ROOT), 0.01F).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityType.NPC.name()
            .toLowerCase(Locale.ROOT), 0.05F).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityType.OTHER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityType.PLAYER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();

        new EntityConfig(Registry.ENTITY_TYPE.getId(EntityType.PIG)
            .toString(), 0).addToSerializables();

        new EntityConfig("lycanite_mobs", 1.2F).addToSerializables();

    }
}
