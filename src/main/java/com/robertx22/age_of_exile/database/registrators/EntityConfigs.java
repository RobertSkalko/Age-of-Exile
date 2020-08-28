package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig.*;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

import static com.robertx22.age_of_exile.database.data.EntityConfig.*;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

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

        EntityConfig golem = new EntityConfig(Registry.ENTITY_TYPE.getId(ModRegistry.ENTITIES.GOLEM_BOSS)
            .toString(), 1);
        golem.max_lvl = 10;
        golem.min_lvl = 10;

        mob(ENTITIES.FIRE_CHICKEN, fire());
        mob(ENTITIES.WATER_CHICKEN, water());
        mob(ENTITIES.NATURE_CHICKEN, nature());
        mob(ENTITIES.THUNDER_CHICKEN, thunder());

        mob(ENTITIES.FIRE_SLIME, fire());
        mob(ENTITIES.WATER_SLIME, water());
        mob(ENTITIES.NATURE_SLIME, nature());
        mob(ENTITIES.THUNDER_SLIME, thunder());
        mob(ENTITIES.ARCANE_SLIME, MANA_BURN);

        mob(ENTITIES.FIRE_SPIDER, fire());
        mob(ENTITIES.WATER_SPIDER, water());
        mob(ENTITIES.NATURE_SPIDER, nature());
        mob(ENTITIES.THUNDER_SPIDER, thunder());

        mob(ENTITIES.ARCANE_SPIDER, MANA_BURN);

        mob(ENTITIES.FIRE_MAGE, new SpecialMobStats(fire(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.BURN)))));
        mob(ENTITIES.WATER_MAGE, new SpecialMobStats(water(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.CHILL)))));
        mob(ENTITIES.NATURE_MAGE, new SpecialMobStats(nature(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.POISON)))));
        mob(ENTITIES.THUNDER_MAGE, new SpecialMobStats(thunder(), new SpecialMobStats(new MobStatData(new StatModifier(100, 100, ChanceToApplyEffect.STATIC)))));

        mob(ENTITIES.FIRE_SKELETON, fire());
        mob(ENTITIES.WATER_SKELETON, water());
        mob(ENTITIES.NATURE_SKELETON, nature());
        mob(ENTITIES.THUNDER_SKELETON, thunder());

        mob(ENTITIES.HEALER_MAGE, new SpecialMobStats(new MobStatData(new StatModifier(-90, -90, HealEffectivenessOnSelf.getInstance()))));

        mob(ENTITIES.FIRE_ZOMBIE, new SpecialMobStats(fire(), LESS_DROPS));
        mob(ENTITIES.WATER_ZOMBIE, new SpecialMobStats(water(), LESS_DROPS));
        mob(ENTITIES.NATURE_ZOMBIE, new SpecialMobStats(nature(), LESS_DROPS));
        mob(ENTITIES.THUNDER_ZOMBIE, new SpecialMobStats(thunder(), LESS_DROPS));
        mob(ENTITIES.ARCANE_ZOMBIE, new SpecialMobStats(MANA_BURN, LESS_DROPS));

    }

    private static void mob(EntityType type, SpecialMobStats stats) {
        EntityConfig c = new EntityConfig(Registry.ENTITY_TYPE.getId(type)
            .toString(), 1);
        c.stats = stats;
        c.addToSerializables();

    }

}
