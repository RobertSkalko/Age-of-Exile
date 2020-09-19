package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectData {

    public UnitData sourceData;
    public UnitData targetData;

    public boolean canceled = false;
    public Unit sourceUnit;
    public Unit targetUnit;

    public LivingEntity source;
    public LivingEntity target;

    public float number = 0;

    protected boolean activateSynergies = true;

    public EffectData(LivingEntity source, LivingEntity target) {

        this.source = source;
        this.target = target;

        if (target != null) {
            this.targetData = Load.Unit(target);
        }
        if (source != null) {
            this.sourceData = Load.Unit(source);

        }
        if (source != null) {

            try {
                if (target != null) {
                    targetUnit = targetData.getUnit();
                }

                sourceUnit = sourceData.getUnit();

                if (sourceUnit != null) {
                    sourceData.tryRecalculateStats(source);

                } else {
                    this.canceled = true;
                }
                if (targetUnit != null) {

                    targetData.tryRecalculateStats(target);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public EffectData(LivingEntity source, LivingEntity target, UnitData sourceData, UnitData targetData) {

        this.source = source;
        this.target = target;

        if (sourceData != null && targetData != null) {
            this.sourceData = sourceData;
            this.targetData = targetData;

            this.sourceUnit = sourceData.getUnit();
            this.targetUnit = targetData.getUnit();

        } else {
            this.canceled = true;
        }

    }

    public EffectTypes effectType = EffectTypes.BASIC_ATTACK;

    public EffectTypes getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectTypes effectType, WeaponTypes weaponType) {
        this.effectType = effectType;
        this.weaponType = weaponType;
    }

    public WeaponTypes weaponType = WeaponTypes.None;

    public enum EffectTypes {
        NORMAL,
        SPELL,
        BASIC_ATTACK,
        BONUS_ATTACK,
        REFLECT,
        DOT_DMG,
    }

    public Unit GetSource() {

        return sourceUnit;
    }

    public Unit GetTarget() {
        return targetUnit;
    }

    public void Activate() {

        calculateEffects();

        if (this.canceled != true) {
            activate();
        }

    }

    boolean effectsCalculated = false;

    public void calculateEffects() {
        if (!effectsCalculated) {
            effectsCalculated = true;
            if (source == null || target == null || canceled == true || sourceUnit == null || targetUnit == null || sourceData == null || targetData == null) {
                return;
            }

            logOnStartData();

            TryApplyEffects(this.GetSource(), EffectSides.Source);

            //  if (GetSource().GUID.equals(GetTarget().GUID) == false) { // todo unsure
            // this makes stats not apply twice if  caster is both target and source.. hmm
            TryApplyEffects(this.GetTarget(), EffectSides.Target);
            // }
            logOnEndData();

        }

    }

    public void logOnStartData() {
        if (MMORPG.statEffectDebuggingEnabled()) {
            System.out.println(
                    Formatting.DARK_PURPLE + "Starting to activate effects for: " + getClass().toString() + " " + "Starting Number: " + number);
        }
    }

    public void logOnEndData() {
        if (MMORPG.statEffectDebuggingEnabled()) {
            System.out.println(
                    Formatting.DARK_PURPLE + "Effects for : " + getClass().toString() + " are finished.");
        }
    }

    public void logAfterEffect(IStatEffect effect) {
        if (MMORPG.statEffectDebuggingEnabled()) {
            System.out.println(Formatting.GREEN + "After : " + Formatting.BLUE + effect.getClass()
                    .toString() + Formatting.WHITE + ": " + this.number);
        }
    }

    protected abstract void activate();

    protected EffectData TryApplyEffects(Unit unit, EffectSides side) {

        if (this.canceled) {
            return this;
        }

        List<EffectUnitStat> Effects = new ArrayList<EffectUnitStat>();

        Effects = AddEffects(Effects, unit, side);

        Effects.sort(new EffectUnitStat());

        for (EffectUnitStat item : Effects) {
            if (item.stat.isNotZero()) {
                if (item.effect.Side()
                        .equals(side)) {
                    item.effect.TryModifyEffect(this, item.source, item.stat, item.stat.GetStat());

                }

            }
        }

        return this;
    }

    /*
    public boolean AffectsThisUnit(IStatEffect effect, EffectSides side, EffectData data, Unit source) {

        boolean affects = false;

        if (effect.Side()
            .equals(EffectSides.Target)) {
            affects = source.equals(data.targetUnit);
        } else {
            affects = source.equals(data.sourceUnit);
        }

        if (affects == false) {
            //System.out.println("works");
        }

        return affects;
    }


     */
    private List<EffectUnitStat> AddEffects(List<EffectUnitStat> effects, Unit unit, EffectSides side) {
        if (unit != null) {
            unit.getStats()
                    .values()
                    .forEach(data -> {
                        if (data.isNotZero()) {
                            Stat stat = data.GetStat();
                            if (stat instanceof IStatEffects) {
                                ((IStatEffects) stat).getEffects()
                                        .forEach(effect -> {
                                            effects.add(new EffectUnitStat(effect, unit, data));
                                        });
                            }
                        }
                    });
        }

        return effects;
    }

}