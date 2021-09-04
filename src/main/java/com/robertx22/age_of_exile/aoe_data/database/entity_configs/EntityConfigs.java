package com.robertx22.age_of_exile.aoe_data.database.entity_configs;

import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.EntityType;

import java.util.Locale;

import static com.robertx22.age_of_exile.database.data.EntityConfig.SpecialMobStats;

public class EntityConfigs implements ExileRegistryInit {

    void setupBroadClasses() {

        new EntityConfig(EntityTypeUtils.EntityClassification.MOB.name()
            .toLowerCase(Locale.ROOT), 1).addToSerializables();
        EntityConfig animal = new EntityConfig(EntityTypeUtils.EntityClassification.ANIMAL.name()
            .toLowerCase(Locale.ROOT), 0);
        animal.hp_multi -= 0.5F;
        animal.addToSerializables();
        EntityConfig npc = new EntityConfig(EntityTypeUtils.EntityClassification.NPC.name()
            .toLowerCase(Locale.ROOT), 0);
        npc.hp_multi += 0.2F;
        npc.addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.OTHER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();
        new EntityConfig(EntityTypeUtils.EntityClassification.PLAYER.name()
            .toLowerCase(Locale.ROOT), 0).addToSerializables();
    }

    void setupSpecificMobs() {

        EntityConfig irongolem = new EntityConfig(EntityType.IRON_GOLEM, 0);
        irongolem.addToSerializables();

        EntityConfig polarbear = new EntityConfig(EntityType.POLAR_BEAR, 0);
        polarbear.addToSerializables();

        new EntityConfig(EntityType.ZOMBIFIED_PIGLIN, 0.5F).addToSerializables();
        new EntityConfig(EntityType.ZOMBIE, 0.75F).addToSerializables();
        new EntityConfig(EntityType.DROWNED, 0.75F).addToSerializables();
        new EntityConfig(EntityType.BEE, 0.8F).addToSerializables();
        new EntityConfig(EntityType.WOLF, 0.5F).addToSerializables();

        EntityConfig enderman = new EntityConfig(EntityType.ENDERMAN, 1);
        enderman.addToSerializables();

        EntityConfig wither = mob(EntityType.WITHER, new SpecialMobStats());
        wither.min_lvl = 30;

        EntityConfig dragon = mob(EntityType.ENDER_DRAGON, new SpecialMobStats());
        dragon.min_lvl = 40;

    }

    void setupWholeMods() {
        new EntityConfig("lycanite_mobs", 1.2F).addToSerializables();

    }

    void setupMyMobs() {

    }

    @Override
    public void registerAll() {
        this.setupBroadClasses();
        this.setupMyMobs();
        this.setupSpecificMobs();
        this.setupWholeMods();
    }

    private EntityConfig mob(EntityType type, SpecialMobStats stats) {
        EntityConfig c = new EntityConfig(type, 1);
        c.stats = stats;
        c.addToSerializables();
        return c;
    }

}
