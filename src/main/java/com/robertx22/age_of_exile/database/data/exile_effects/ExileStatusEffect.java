package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;

import java.util.List;

public class ExileStatusEffect extends StatusEffect implements IGUID, IApplyStatPotion {

    int numericId;
    String numericIdString;

    public ExileStatusEffect(int numericId) {
        super(StatusEffectType.NEUTRAL, 0);
        this.numericId = numericId;
        this.numericIdString = numericId + "";
    }

    public boolean hasExileRegistry() {
        return SlashRegistry.ExileEffects()
                .isRegistered(numericIdString);
    }

    public ExileEffect getExileEffect() {
        return SlashRegistry.ExileEffects()
                .get(numericIdString);
    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en)
                .getStatusEffectsData()
                .get(this);
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        ExileEffect exect = getExileEffect();
        exect.mc_stats.forEach(x -> x.apply(entity));

        super.onApplied(entity, attributes, amplifier);

    }

    @Override
    public void onRemoved(LivingEntity target, AttributeContainer attributes,
                          int amplifier) {

        try {
            ExileEffect exect = getExileEffect();
            exect.mc_stats.forEach(x -> x.remove(target));

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
            ExileEffectInstanceData data = getSavedData(entity);

            if (data == null || data.spellData == null) {
                return;
            }

            LivingEntity caster = data.spellData.getCaster(entity.world);
            if (caster == null) {
                return;
            }

            SpellCtx ctx = SpellCtx.onTick(caster, entity, data.spellData);
            exect.spell.tryActivate(Spell.Builder.DEFAULT_EN_NAME, ctx); // source is default name at all times
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyStats(World world, StatusEffectInstance instance, LivingEntity target) {
        ExileEffectInstanceData data = getSavedData(target);

        if (data != null && data.spellData != null && data.spellData.getCaster(world) != null) {
            int casterlvl = Load.Unit(data.spellData.getCaster(world))
                    .getLevel();
            getExileEffect().stats.forEach(x -> x.applyStats(Load.Unit(target), casterlvl));
        }
    }

    @Override
    public List<PotionStat> getPotionStats() {
        return null; // remove later
    }

    @Override
    public String GUID() {
        return numericIdString;
    }

}
