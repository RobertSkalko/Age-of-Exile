package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExileStatusEffect extends Effect implements IGUID, IApplyableStats, IOneOfATypePotion {

    String exileEffectId;

    public EffectType type;

    public ExileStatusEffect(EffectType type, int numericId) {
        super(type.type, 0);
        this.exileEffectId = getIdPath(type, numericId);
        this.type = type;
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
        return ExileDB.ExileEffects()
            .isRegistered(exileEffectId);
    }

    public ExileEffect getExileEffect() {
        return ExileDB.ExileEffects()
            .get(exileEffectId);
    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en)
            .getStatusEffectsData()
            .get(this);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager attributes, int amplifier) {

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

        super.addAttributeModifiers(entity, attributes, amplifier);

    }

    @Override
    public void removeAttributeModifiers(LivingEntity target, AttributeModifierManager attributes,
                                         int amplifier) {

        try {

            ExileEffect exect = getExileEffect();
            exect.mc_stats.forEach(x -> x.removeVanillaStats(target));

            ExileEffectInstanceData data = getSavedData(target);

            if (data != null && data.spellData != null) {
                LivingEntity caster = data.spellData.getCaster(target.level);
                if (caster != null && exect.spell != null) {
                    SpellCtx ctx = SpellCtx.onExpire(caster, target, data.spellData);

                    exect.spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
                }
            }

            EntityData unitdata = Load.Unit(target);
            unitdata.getStatusEffectsData()
                .get(this).stacks = 0;
            unitdata.setEquipsChanged(true);

            super.removeAttributeModifiers(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        try {
            if (entity.isDeadOrDying()) {
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

            LivingEntity caster = data.spellData.getCaster(entity.level);
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

                getExileEffect().getExactStats(en.level, data.spellData)
                    .stream()
                    .forEach(x -> stats.add(x));
            }
        }

        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.POTION_EFFECT, stats));
    }

    @Override
    public String getOneOfATypeType() {
        return getExileEffect().one_of_a_kind_id;
    }
}
