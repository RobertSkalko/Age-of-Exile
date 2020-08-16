package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class EntityConfigs implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new EntityConfig(EntityTypeUtils.EntityClassification.MOB.name()
            .toLowerCase(Locale.ROOT), 1).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.ANIMAL.name()
            .toLowerCase(Locale.ROOT), 0.01F).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.NPC.name()
            .toLowerCase(Locale.ROOT), 0.05F).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.OTHER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.PLAYER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();

        new EntityConfig(Registry.ENTITY_TYPE.getId(EntityType.PIG)
            .toString(), 0).addToSerializables();

        new EntityConfig("lycanite_mobs", 1.2F).addToSerializables();

    }
}
