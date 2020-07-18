package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.database.data.spells.entities.cloud.ArrowStormEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.cloud.BlizzardEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.cloud.ThunderstormEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.cloud.VolcanoEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.*;
import com.robertx22.mine_and_slash.database.data.spells.entities.single_target_bolt.FireballEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.single_target_bolt.FrostballEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.single_target_bolt.PoisonBallEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.trident.SpearOfJudgementEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.trident.ThunderspearEntity;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {

    public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public static List<EntityType<? extends Entity>> BOSSES = new LinkedList();
    public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public static EntityType randomBoss() {
        return RandomUtils.randomFromList(BOSSES);
    }

    @SubscribeEvent
    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event) {

        ENTITY_TYPES.forEach(entityType -> event.getRegistry()
            .register(entityType));

    }

    public static final EntityType<? extends Entity> THUNDERSTORM;
    public static final EntityType<? extends TridentEntity> THUNDER_SPEAR;
    public static final EntityType<? extends TridentEntity> HOLY_SPEAR;
    public static final EntityType<? extends Entity> LIGHTNING_TOTEM;

    public static final EntityType<? extends Entity> FIREBOLT;
    public static final EntityType<? extends Entity> FIRE_BOMB;
    public static final EntityType<? extends Entity> THROW_FLAMES;
    public static final EntityType<? extends Entity> VOLCANO;

    public static final EntityType<? extends Entity> POISON_BALL;

    public static final EntityType<? extends Entity> FROSTBOLT;
    public static final EntityType<? extends Entity> WHIRPOOL;
    public static final EntityType<? extends Entity> BLIZZARD;
    public static final EntityType<? extends Entity> TIDAL_WAVE;

    public static final EntityType<RangerArrowEntity> RANGER_ARROW;
    public static final EntityType<? extends Entity> ARROW_STORM;

    public static final EntityType<? extends Entity> DIVINE_TRIBULATION;

    public static final EntityType<? extends Entity> SEED;

    static {

        HOLY_SPEAR = projectile(SpearOfJudgementEntity::new, SpearOfJudgementEntity::new, "holy_spear", false);

        BLIZZARD = projectile(BlizzardEntity::new, BlizzardEntity::new, "blizzard");
        FROSTBOLT = projectile(FrostballEntity::new, FrostballEntity::new, "frostball");
        WHIRPOOL = projectile(WhirlpoolEntity::new, WhirlpoolEntity::new, "whirlpool");
        TIDAL_WAVE = projectile(TidalWaveEntity::new, TidalWaveEntity::new, "tidal_wave");

        POISON_BALL = projectile(PoisonBallEntity::new, PoisonBallEntity::new, "poison_ball");

        THUNDERSTORM = projectile(ThunderstormEntity::new, ThunderstormEntity::new, "thunderstorm");
        THUNDER_SPEAR = projectile(ThunderspearEntity::new, ThunderspearEntity::new, "thunder_spear", false);
        LIGHTNING_TOTEM = projectile(LightningTotemEntity::new, LightningTotemEntity::new, "lightning_totem");

        FIREBOLT = projectile(FireballEntity::new, FireballEntity::new, "fireball");
        FIRE_BOMB = projectile(FireBombEntity::new, FireBombEntity::new, "fire_bomb");
        THROW_FLAMES = projectile(ThrowFlameEntity::new, ThrowFlameEntity::new, "seeker_flame");
        VOLCANO = projectile(VolcanoEntity::new, VolcanoEntity::new, "volcano");

        RANGER_ARROW = projectile(RangerArrowEntity::new, RangerArrowEntity::new, "ranger_arrow");
        ARROW_STORM = projectile(ArrowStormEntity::new, ArrowStormEntity::new, "arrow_storm");

        DIVINE_TRIBULATION = projectile(DivineTribulationEntity::new, DivineTribulationEntity::new, "divine_tribulation");

        SEED = projectile(SeedEntity::new, SeedEntity::new, "seed_entity");

    }

    public static EntityType addBoss(EntityType type, String id) {
        type.setRegistryName(new Identifier(Ref.MODID, id));
        ENTITY_TYPES.add(type);
        BOSSES.add(type);
        return type;
    }

    private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                               BiFunction<FMLPlayMessages.SpawnEntity, World, T> bif,
                                                               String id) {

        return projectile(factory, bif, id, true);

    }

    private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                               BiFunction<FMLPlayMessages.SpawnEntity, World, T> bif,
                                                               String id, boolean itemRender) {

        EntityType<T> type = EntityType.Builder.<T>create(factory, EntityCategory.MISC).setCustomClientFactory(
            bif)
            .setDimensions(0.5F, 0.5F)
            .build(Ref.MODID + ":" + id.toLowerCase(Locale.ROOT));

        type.setRegistryName(new Identifier(Ref.MODID, id.toLowerCase(Locale.ROOT)));

        ENTITY_TYPES.add(type);

        if (itemRender) {
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);
        }

        return type;
    }

}


