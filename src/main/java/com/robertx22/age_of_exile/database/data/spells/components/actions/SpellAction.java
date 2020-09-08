package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
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

    public static SummonProjectileAction SUMMON_PROJECTILE = of(new SummonProjectileAction());
    public static DamageAction DEAL_DAMAGE = of(new DamageAction());
    public static ParticleInRadiusAction PARTICLES_IN_RADIUS = of(new ParticleInRadiusAction());
    public static SoundAction PLAY_SOUND = of(new SoundAction());
    public static PlaySoundPerTarget PLAY_SOUND_PER_TARGET = of(new PlaySoundPerTarget());
    public static ExilePotionAction EXILE_POTION = of(new ExilePotionAction());
    public static SummonAtSightAction SUMMON_AT_SIGHT = of(new SummonAtSightAction());
    public static SummonLightningStrikeAction SUMMON_LIGHTNING_STRIKE = of(new SummonLightningStrikeAction());
    public static SummonBlockAction SUMMON_BLOCK = of(new SummonBlockAction());

    private static <T extends SpellAction> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }
}
