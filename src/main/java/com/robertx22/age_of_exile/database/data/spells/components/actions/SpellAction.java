package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.special.IceNovaAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.*;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.entity.LivingEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class SpellAction extends BaseFieldNeeder implements IGUID {

    public SpellAction(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data);

    public static HashMap<String, SpellAction> MAP = new HashMap<>();

    public static SummonEvokerFangsAction SUMMON_EVOKER_FANG_AT_TARGET = of(new SummonEvokerFangsAction());
    public static RefreshCooldownsAction REFRESH_COOLDOWNS_BY_X_TICKS = of(new RefreshCooldownsAction());
    public static SetOnCooldownAction SET_ON_COOLDOWN = of(new SetOnCooldownAction());
    public static ExpireAction EXPIRE = of(new ExpireAction());
    public static DoActionForEachEffectOnTarget DO_ACTION_FOR_EACH_EFFECT_WITH_TAG_ON_TARGET = of(new DoActionForEachEffectOnTarget());
    public static DoSpecificAction SPECIFIC_ACTION = of(new DoSpecificAction());
    public static RideAction RIDE = of(new RideAction());
    public static OpenEnderChestAction OPEN_ENDER_CHEST = of(new OpenEnderChestAction());
    public static SummonProjectileAction SUMMON_PROJECTILE = of(new SummonProjectileAction());
    public static DamageAction DEAL_DAMAGE = of(new DamageAction());
    public static ParticleInRadiusAction PARTICLES_IN_RADIUS = of(new ParticleInRadiusAction());
    public static SoundAction PLAY_SOUND = of(new SoundAction());
    public static PlaySoundPerTarget PLAY_SOUND_PER_TARGET = of(new PlaySoundPerTarget());
    public static SummonAtSightAction SUMMON_AT_SIGHT = of(new SummonAtSightAction());
    public static SummonLightningStrikeAction SUMMON_LIGHTNING_STRIKE = of(new SummonLightningStrikeAction());
    public static SummonBlockAction SUMMON_BLOCK = of(new SummonBlockAction());
    public static PushAction PUSH = of(new PushAction());
    public static SwordSweepParticlesAction SWORD_SWEEP_PARTICLES = of(new SwordSweepParticlesAction());
    public static PotionAction POTION = of(new PotionAction());
    public static TeleportCasterToSightAction TP_CASTER_IN_DIRECTION = of(new TeleportCasterToSightAction());
    public static RestoreHealthAction RESTORE_HEALTH = of(new RestoreHealthAction());
    public static RestoreManaAction RESTORE_MANA = of(new RestoreManaAction());
    public static CancelCastAction CANCEL_CAST = of(new CancelCastAction());
    public static SpellMotionAction SET_ADD_MOTION = of(new SpellMotionAction());
    public static GiveShieldAction GIVE_SHIELD = of(new GiveShieldAction());
    public static CasterCommandAction CASTER_USE_COMMAND = of(new CasterCommandAction());
    public static ExileEffectAction EXILE_EFFECT = of(new ExileEffectAction());
    public static AggroAction AGGRO = of(new AggroAction());
    public static SummonAtTargetFeet SUMMON_AT_FEET = of(new SummonAtTargetFeet());
    public static TeleportTargetToSourceAction TP_TARGET_TO_SELF = of(new TeleportTargetToSourceAction());
    public static IceNovaAction ICE_NOVA = of(new IceNovaAction());
    public static EffectCloudAction POTION_AREA_PARTICLES = of(new EffectCloudAction());

    private static <T extends SpellAction> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }
}
