package com.robertx22.age_of_exile.aoe_data.database.entity_configs;

import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig.*;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.entity.EntityType;

import java.util.Locale;

import static com.robertx22.age_of_exile.database.data.EntityConfig.*;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class EntityConfigs implements ISlashRegistryInit {

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
    }

    void setupWholeMods() {
        new EntityConfig("lycanite_mobs", 1.2F).addToSerializables();

    }

    void setupMyMobs() {
        EntityConfig golem = mob(ModRegistry.ENTITIES.GOLEM_BOSS, new SpecialMobStats());
        golem.max_lvl = 10;
        golem.min_lvl = 10;
        golem.min_rarity = IRarity.Boss;
        golem.max_rarity = IRarity.Boss;

        mob(ENTITIES.FIRE_CHICKEN, fire());
        mob(ENTITIES.WATER_CHICKEN, water());
        mob(ENTITIES.NATURE_CHICKEN, nature());
        mob(ENTITIES.THUNDER_CHICKEN, thunder());

        mob(ENTITIES.FIRE_MAGE, new SpecialMobStats(fire(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.BURN)))));
        mob(ENTITIES.WATER_MAGE, new SpecialMobStats(water(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.CHILL)))));
        mob(ENTITIES.NATURE_MAGE, new SpecialMobStats(nature(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.POISON)))));
        mob(ENTITIES.THUNDER_MAGE, new SpecialMobStats(thunder(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.STATIC)))));

        mob(ENTITIES.HEALER_MAGE, new SpecialMobStats(new MobStatData(new StatModifier(-90, -90, HealEffectivenessOnSelf.getInstance()))));

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
