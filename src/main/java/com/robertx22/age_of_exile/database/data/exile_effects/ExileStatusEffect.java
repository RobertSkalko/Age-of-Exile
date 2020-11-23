package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IApplyStatPotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;

public class ExileStatusEffect extends StatusEffect implements IGUID, IApplyStatPotion {

    String exileEffectId;

    public ExileStatusEffect(StatusEffectType type, int numericId) {
        super(type, 0);
        this.exileEffectId = getIdPath(type, numericId);
    }

    public static String getIdPath(StatusEffectType type, int num) {

        if (type == StatusEffectType.BENEFICIAL) {
            return "beneficial/" + num;
        }
        if (type == StatusEffectType.HARMFUL) {
            return "negative/" + num;
        }
        return "neutral/" + num;
    }

    public boolean hasExileRegistry() {
        return SlashRegistry.ExileEffects()
            .isRegistered(exileEffectId);
    }

    public ExileEffect getExileEffect() {
        return SlashRegistry.ExileEffects()
            .get(exileEffectId);
    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en)
            .getStatusEffectsData()
            .get(this);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        ExileEffect exect = getExileEffect();
        ExileEffectInstanceData data = getSavedData(entity);

        int stacks = data.stacks;

        exect.mc_stats.forEach(x -> x.applyVanillaStats(entity, stacks));

        Load.Unit(entity)
            .forceRecalculateStats();

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
                .set(this, null);
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
    public void applyStats(World world, StatusEffectInstance instance, LivingEntity target) {
        ExileEffectInstanceData data = getSavedData(target);

        ExileEffect exect = getExileEffect();
        int stacks = data.stacks;

        if (data != null && data.spellData != null && data.spellData.getCaster(world) != null) {
            int casterlvl = Load.Unit(data.spellData.getCaster(world))
                .getLevel();
            getExileEffect().stats.stream()
                .map(x -> new OptScaleExactStat(x.first * stacks, x.second * stacks, x.getStat(), x.getModType()))
                .forEach(x -> x.applyStats(Load.Unit(target), casterlvl));
        }
    }

    @Override
    public String GUID() {
        return exileEffectId;
    }

}
