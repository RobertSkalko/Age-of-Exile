package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileAmountStat;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
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
                    sourceData.tryRecalculateStats();

                } else {
                    this.canceled = true;
                }
                if (targetUnit != null) {

                    targetData.tryRecalculateStats();

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

    public AttackType attackType = AttackType.ATTACK;

    public AttackType getAttackType() {
        return attackType;
    }

    public WeaponTypes weaponType = WeaponTypes.None;

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

            TryApplyEffects(this.sourceData, EffectSides.Source);

            //  if (GetSource().GUID.equals(GetTarget().GUID) == false) { // todo unsure
            // this makes stats not apply twice if  caster is both target and source.. hmm
            TryApplyEffects(this.targetData, EffectSides.Target);
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

    protected void TryApplyEffects(UnitData data, EffectSides side) {

        if (this.canceled) {
            return;
        }

        List<EffectUnitStat> Effects = new ArrayList<EffectUnitStat>();

        Effects = AddEffects(Effects, data);

        addSkillGemEffects(Effects, data);

        Effects.sort(EffectUnitStat.COMPARATOR);

        for (EffectUnitStat item : Effects) {
            if (item.stat.isNotZero()) {
                if (item.effect.Side()
                    .equals(side)) {
                    item.effect.TryModifyEffect(this, item.source, item.stat, item.stat.GetStat());
                }
            }
        }

    }

    private void addSkillGemEffects(List<EffectUnitStat> effects, UnitData data) {

        if (this instanceof IHasSpellEffect) {
            IHasSpellEffect has = (IHasSpellEffect) this;

            // todo

            if (MMORPG.RUN_DEV_TOOLS) {

                Stat stat = ProjectileAmountStat.getInstance();

                StatData statData = new StatData(stat.GUID(), 5, 5);

                effects.add(new EffectUnitStat(stat.statEffect, data.getUnit(), statData));

            }

        }

    }

    private List<EffectUnitStat> AddEffects(List<EffectUnitStat> effects, UnitData unit) {
        if (unit != null) {
            unit.getUnit()
                .getStats()
                .values()
                .forEach(data -> {
                    if (data.isNotZero()) {
                        Stat stat = data.GetStat();

                        if (stat.statEffect != null) {
                            effects.add(new EffectUnitStat(stat.statEffect, unit.getUnit(), data));
                        }

                        if (stat instanceof IStatEffects) {
                            ((IStatEffects) stat).getEffects()
                                .forEach(effect -> {
                                    effects.add(new EffectUnitStat(effect, unit.getUnit(), data));
                                });
                        }
                    }
                });
        }

        return effects;
    }

}