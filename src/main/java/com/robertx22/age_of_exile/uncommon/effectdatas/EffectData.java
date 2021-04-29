package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect.EffectSides;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectData {

    public UnitData sourceData;
    public UnitData targetData;

    public Unit sourceUnit;
    public Unit targetUnit;

    public LivingEntity source;
    public LivingEntity target;

    private boolean effectsCalculated = false;

    public EventData data = new EventData();

    public EffectData(float num, LivingEntity source, LivingEntity target) {
        this(source, target);

        this.data.getNumber(EventData.NUMBER).number = num;
        this.data.getNumber(EventData.ORIGINAL_VALUE).number = num;

    }

    public EffectData(LivingEntity source, LivingEntity target) {

        this.source = source;
        this.target = target;

        if (target != null && source != null) {
            this.targetData = Load.Unit(target);
            targetUnit = targetData.getUnit();

            this.sourceData = Load.Unit(source);
            sourceUnit = sourceData.getUnit();
        } else {
            this.data.setBoolean(EventData.CANCELED, true);
        }

        if (sourceUnit == null || targetUnit == null) {
            this.data.setBoolean(EventData.CANCELED, true);
        }

    }

    public void increaseByPercent(float perc) {
        data.getNumber(EventData.NUMBER).number += data.getNumber(EventData.ORIGINAL_VALUE).number * perc / 100F;
    }

    public void Activate() {

        calculateEffects();

        if (!data.isCanceled()) {
            activate();
        }

    }

    public void calculateEffects() {
        if (!effectsCalculated) {
            effectsCalculated = true;
            if (source == null || target == null || data.getBoolean(EventData.CANCELED) == true || sourceUnit == null || targetUnit == null || sourceData == null || targetData == null) {
                return;
            }
            TryApplyEffects(source, sourceData, EffectSides.Source);
            TryApplyEffects(target, targetData, EffectSides.Target);
        }

    }

    protected abstract void activate();

    protected void TryApplyEffects(LivingEntity en, UnitData data, EffectSides side) {

        if (this.data.getBoolean(EventData.CANCELED)) {
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
            try {
                if (this instanceof IHasSpellEffect) {
                    IHasSpellEffect has = (IHasSpellEffect) this;

                    Spell spell = has.getSpell();

                    PlayerSpellCap.ISpellsCap spells = Load.spells((PlayerEntity) en);

                    int place = -1;

                    for (int i = 0; i < spells.getSkillGemData().stacks.size(); i++) {

                        ItemStack stack = spells.getSkillGemData().stacks.get(i);

                        SkillGemData sd = SkillGemData.fromStack(stack);
                        if (sd != null && sd.getSkillGem() != null && sd.getSkillGem().spell_id.equals(spell.GUID())) {
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
            } catch (Exception e) {
                e.printStackTrace();
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

                        if (stat instanceof IExtraStatEffect) {
                            ((IExtraStatEffect) stat).getEffects()
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