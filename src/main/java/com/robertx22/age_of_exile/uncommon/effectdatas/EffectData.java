package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

            TryApplyEffects(source, sourceData, EffectSides.Source);

            //  if (GetSource().GUID.equals(GetTarget().GUID) == false) { // todo unsure
            // this makes stats not apply twice if  caster is both target and source.. hmm
            TryApplyEffects(target, targetData, EffectSides.Target);
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

    protected void TryApplyEffects(LivingEntity en, UnitData data, EffectSides side) {

        if (this.canceled) {
            return;
        }

        List<EffectUnitStat> Effects = new ArrayList<EffectUnitStat>();

        Effects = AddEffects(Effects, data, en);

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

    private Unit.StatContainerType getStatType(LivingEntity en, UnitData data) {

        if (en instanceof PlayerEntity) {
            if (this instanceof IHasSpellEffect) {
                IHasSpellEffect has = (IHasSpellEffect) this;

                Spell spell = has.getSpell();

                PlayerSpellCap.ISpellsCap spells = Load.spells(en);

                int place = -1;

                for (int i = 0; i < spells.getSkillGemData().stacks.size(); i++) {

                    ItemStack stack = spells.getSkillGemData().stacks.get(i);

                    SkillGemData sd = SkillGemData.fromStack(stack);
                    if (sd != null && sd.getSkillGem().spell_id.equals(spell.GUID())) {
                        for (SkillGemsData.Places p : SkillGemsData.Places.values()) {
                            if (p.index == i) {
                                place = p.place;
                            }
                        }
                    }

                }

                if (place > -1) {

                    if (place == 0) {
                        return Unit.StatContainerType.SPELL1;
                    }
                    if (place == 1) {
                        return Unit.StatContainerType.SPELL2;
                    }
                    if (place == 2) {
                        return Unit.StatContainerType.SPELL3;
                    }
                    if (place == 3) {
                        return Unit.StatContainerType.SPELL4;
                    }
                }

            }
        }

        return Unit.StatContainerType.NORMAL;

    }

    private List<EffectUnitStat> AddEffects(List<EffectUnitStat> effects, UnitData unit, LivingEntity en) {

        Unit.StatContainerType type = getStatType(en, unit);

        if (unit != null) {
            unit.getUnit()
                .getStats(type).stats
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