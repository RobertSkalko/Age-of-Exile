package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExileStatusEffect extends StatusEffect implements IGUID, IApplyableStats, IOneOfATypePotion {

    String exileEffectId;

    public ExileStatusEffect(EffectType type, int numericId) {
        super(type.type, 0);
        this.exileEffectId = getIdPath(type, numericId);
    }

    public static String getIdPath(EffectType type, int num) {
        if (type == EffectType.beneficial) {
            return "beneficial/" + num;
        }
        if (type == EffectType.negative) {
            return "negative/" + num;
        }
        if (type == EffectType.buff) {
            return "buff/" + num;
        }

        return "neutral/" + num;
    }

    public boolean hasExileRegistry() {
        return Database.ExileEffects()
            .isRegistered(exileEffectId);
    }

    public ExileEffect getExileEffect() {
        return Database.ExileEffects()
            .get(exileEffectId);
    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en)
            .getStatusEffectsData()
            .get(this);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        try {
            ExileEffect exect = getExileEffect();
            ExileEffectInstanceData data = getSavedData(entity);

            if (data != null) {
                int stacks = data.stacks;

                exect.mc_stats.forEach(x -> x.applyVanillaStats(entity, stacks));

                Load.Unit(entity)
                    .forceRecalculateStats();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onApplied(entity, attributes, amplifier);

    }

    @Override
    public void onRemoved(LivingEntity target, AttributeContainer attributes,
                          int amplifier) {

        try {

            ExileEffect exect = getExileEffect();
            exect.mc_stats.forEach(x -> x.removeVanillaStats(target));

            ExileEffectInstanceData data = getSavedData(target);

            if (data != null && data.spellData != null) {
                LivingEntity caster = data.spellData.getCaster(target.world);
                if (caster != null && exect.spell != null) {
                    SpellCtx ctx = SpellCtx.onExpire(caster, target, data.spellData);

                    exect.spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
                }
            }

            EntityCap.UnitData unitdata = Load.Unit(target);
            unitdata.getStatusEffectsData()
                .get(this).stacks = 0;
            unitdata.setEquipsChanged(true);

            super.onRemoved(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        try {
            if (entity.isDead()) {
                return;
            }

            ExileEffect exect = getExileEffect();

            if (exect.spell == null) {
                return;
            }

            ExileEffectInstanceData data = getSavedData(entity);

            if (data == null || data.spellData == null) {
                return;
            }

            LivingEntity caster = data.spellData.getCaster(entity.world);
            if (caster == null) {
                return;
            }

            SpellCtx ctx = SpellCtx.onTick(caster, entity, data.spellData);
            exect.spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String GUID() {
        return exileEffectId;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {

        List<ExactStatData> stats = new ArrayList<>();

        ExileEffectInstanceData data = getSavedData(en);
        if (data != null) {
            int stacks = data.stacks;

            if (data.spellData != null) {
                int casterlvl = data.spellData.lvl;
                getExileEffect().stats.stream()
                    .map(x -> new OptScaleExactStat(x.v1 * stacks * data.str_multi, x.v2 * stacks * data.str_multi, x.getStat(), x.getModType()))
                    .forEach(x -> stats.add(x.ToExactScaleToLevel(casterlvl)));
            }
        }

        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.POTION_EFFECT, stats));
    }

    @Override
    public String getOneOfATypeType() {
        return getExileEffect().one_of_a_kind_id;
    }
}
