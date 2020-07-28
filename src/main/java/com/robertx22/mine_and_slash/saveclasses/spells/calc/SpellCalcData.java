package com.robertx22.mine_and_slash.saveclasses.spells.calc;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SpellCalcData {

    public static SpellCalcData empty() {

        SpellCalcData d = new SpellCalcData();
        d.empty = true;

        return d;
    }

    public static SpellCalcData base(float base) {
        SpellCalcData data = new SpellCalcData();

        data.baseValue = base;

        return data;
    }

    public static SpellCalcData scaleWithAttack(float attack, float base) {
        SpellCalcData data = new SpellCalcData();

        List<Stat> list = new WeaponDamage(Elements.Nature).generateAllSingleVariations();
        list.add(new WeaponDamage(Elements.Physical));
        data.mergedScalingValues.add(new MergedScalingStatsCalc(list, attack, new SText(Formatting.GOLD + "Attack Damage")));

        data.baseValue = base;

        return data;
    }

    @Factory
    private SpellCalcData() {

    }

    public SpellCalcData(ScalingStatCalc calc, int base) {
        this.scalingValues.add(calc);
        this.baseValue = base;
    }

    public double getScalingMultiAverage() {
        return getAllScalingValues().stream()
            .mapToDouble(x -> x.getMulti())
            .sum() / scalingValues.size();

    }

    public List<BaseStatCalc> getAllScalingValues() {
        List<BaseStatCalc> list = new ArrayList<>();
        list.addAll(scalingValues);
        list.addAll(mergedScalingValues);

        return list;
    }

    @Store
    public List<ScalingStatCalc> scalingValues = new ArrayList<>();

    @Store
    public List<MergedScalingStatsCalc> mergedScalingValues = new ArrayList<>();

    @Store
    public StatScaling baseScaling = StatScaling.SCALING;

    private boolean empty = false;

    @Store
    public float baseValue = 0;

    public int getCalculatedBaseValue(int lvl) {
        return (int) baseScaling.scale(baseValue, lvl);
    }

    private int getCalculatedScalingValue(EntityCap.UnitData data) {
        return getAllScalingValues().stream()
            .mapToInt(x -> x.getCalculatedValue(data))
            .sum();
    }

    public int getCalculatedValue(EntityCap.UnitData data, SkillGemData skillgem) {
        int val = getCalculatedScalingValue(data);
        val += getCalculatedBaseValue(skillgem.level);
        return val;
    }

    public int getCalculatedValue(LivingEntity caster) {
        int val = getCalculatedScalingValue(Load.Unit(caster));
        val += getCalculatedBaseValue(Load.Unit(caster)
            .getLevel());
        return val;
    }

    public List<Text> GetTooltipString(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        if (!empty) {
            getAllScalingValues().forEach(x -> list.addAll(x.GetTooltipString(info)));

            if (baseValue > 0) {
                list.add(new LiteralText(
                    Formatting.RED + "Base Value: " + getCalculatedBaseValue(ctx.skillGem.level)));
            }
        }

        return list;
    }

    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();

        if (!empty) {
            getAllScalingValues().forEach(x -> list.addAll(x.GetTooltipString(info)));

            if (baseValue > 0) {
                list.add(new LiteralText(
                    Formatting.RED + "Base Value: " + getCalculatedBaseValue(info.unitdata.getLevel())));
            }
        }

        return list;
    }

}
