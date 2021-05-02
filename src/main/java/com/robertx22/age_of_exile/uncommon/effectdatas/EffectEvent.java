package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectEvent implements IGUID {

    public UnitData sourceData;
    public UnitData targetData;

    public LivingEntity source;
    public LivingEntity target;

    private boolean effectsCalculated = false;

    public EventData data = new EventData();

    public EffectEvent(float num, LivingEntity source, LivingEntity target) {
        this(source, target);

        this.data.getNumber(EventData.NUMBER).number = num;
        this.data.getNumber(EventData.ORIGINAL_VALUE).number = num;

    }

    public EffectEvent(LivingEntity source, LivingEntity target) {

        this.source = source;
        this.target = target;

        if (target != null && source != null) {
            this.targetData = Load.Unit(target);
            this.sourceData = Load.Unit(source);
        } else {
            this.data.setBoolean(EventData.CANCELED, true);
        }
    }

    public boolean isSpell() {
        return data.isSpellEffect();
    }

    public Spell getSpell() {
        return Database.Spells()
            .get(data.getString(EventData.SPELL));
    }

    public void increaseByPercent(float perc) {
        data.getNumber(EventData.NUMBER).number += data.getNumber(EventData.ORIGINAL_VALUE).number * perc / 100F;
    }

    public void Activate() {

        //Watch watch = new Watch();
        //watch.min = 500;

        calculateEffects();

        if (!data.isCanceled()) {
            activate();
        }

        // watch.print("stat events " + GUID() + " ");

    }

    public void calculateEffects() {
        if (source.world.isClient) {
            return; // todo is this fine? spell calc seems to be called on client every tick!
        }
        if (!effectsCalculated) {
            effectsCalculated = true;
            if (target == null || data.isCanceled() || sourceData == null || targetData == null) {
                return;
            }
            TryApplyEffects(source, sourceData, EffectSides.Source);
            TryApplyEffects(target, targetData, EffectSides.Target);
        }

    }

    protected abstract void activate();

    protected void TryApplyEffects(LivingEntity en, UnitData data, EffectSides side) {

        if (this.data.isCanceled()) {
            return;
        }

        List<EffectWithCtx> effectsWithCtx = new ArrayList<>();

        effectsWithCtx = AddEffects(effectsWithCtx, data, en, side);

        effectsWithCtx.sort(EffectWithCtx.COMPARATOR);

        for (EffectWithCtx item : effectsWithCtx) {
            if (item.stat.isNotZero()) {
                if (item.effect.Side()
                    .equals(side)) {
                    item.effect.TryModifyEffect(this, item.statSource, item.stat, item.stat.GetStat());
                } else {
                    System.out.print("ERORR Stat at wrong side should never be added in the first place! ");
                }
            } else {
                System.out.print("ERORR cant be zero! ");
            }
        }

    }

    public LivingEntity getSide(EffectSides side) {
        if (side == EffectSides.Source) {
            return source;
        } else {
            return target;
        }
    }

    private Unit.StatContainerType getStatType(LivingEntity en, UnitData data) {

        if (en instanceof PlayerEntity) {
            try {
                if (this.data.isSpellEffect()) {

                    Spell spell = getSpell();

                    EntitySpellCap.ISpellsCap spells = Load.spells(en);

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

    private List<EffectWithCtx> AddEffects(List<EffectWithCtx> effects, UnitData unit, LivingEntity en, EffectSides side) {

        Unit.StatContainerType type = getStatType(en, unit);

        if (unit != null) {
            unit.getUnit()
                .getStats(type).stats
                .values()
                .forEach(data -> {
                    if (data.isNotZero()) {
                        Stat stat = data.GetStat();

                        if (stat.statEffect != null) {
                            if (stat.statEffect.Side()
                                .equals(side)) {
                                effects.add(new EffectWithCtx(stat.statEffect, side, data));
                            }
                        }

                        if (stat instanceof DatapackStat) {
                            DatapackStat d = (DatapackStat) stat;
                            if (d.effect != null) {
                                if (d.effect.Side()
                                    .equals(side)) {
                                    effects.add(new EffectWithCtx(d.effect, side, data));
                                }
                            }
                        }
                    }
                });
        }

        return effects;
    }

    @Override
    public String formattedGUID() {
        return GUID();
    }

}