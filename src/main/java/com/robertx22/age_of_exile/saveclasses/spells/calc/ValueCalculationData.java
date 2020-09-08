package com.robertx22.age_of_exile.saveclasses.spells.calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
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
public class ValueCalculationData {

    public static ValueCalculationData empty() {
        ValueCalculationData d = new ValueCalculationData();
        d.empty = true;
        return d;
    }

    public static ValueCalculationData base(float base) {
        ValueCalculationData data = new ValueCalculationData();

        data.base_val = base;

        return data;
    }

    public static ValueCalculationData scaleWithAttack(float attack, float base) {
        ValueCalculationData data = new ValueCalculationData();

        new WeaponDamage(Elements.Nature).generateAllSingleVariations()
            .forEach(x ->
                data.scaling_values.add(new ScalingStatCalc(x, attack)));

        data.base_val = base;

        return data;
    }

    @Factory
    public ValueCalculationData() {

    }

    public ValueCalculationData(ScalingStatCalc calc, int base) {
        this.scaling_values.add(calc);
        this.base_val = base;
    }

    public double getScalingMultiAverage() {
        return getAllScalingValues().stream()
            .mapToDouble(x -> x.getMulti())
            .sum() / scaling_values.size();

    }

    public List<BaseStatCalc> getAllScalingValues() {
        List<BaseStatCalc> list = new ArrayList<>();
        list.addAll(scaling_values);
        return list;
    }

    @Store
    public List<ScalingStatCalc> scaling_values = new ArrayList<>();

    @Store
    public StatScaling base_scaling = StatScaling.SCALING;

    private transient boolean empty = false;

    @Store
    public float base_val = 0;

    public int getCalculatedBaseValue(int lvl) {
        return (int) base_scaling.scale(base_val, lvl);
    }

    private int getCalculatedScalingValue(EntityCap.UnitData data) {
        return getAllScalingValues().stream()
            .mapToInt(x -> x.getCalculatedValue(data))
            .sum();
    }

    public int getCalculatedValue(EntityCap.UnitData data, CalculatedSpellData skillgem) {
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

            if (base_val > 0) {
                list.add(new LiteralText(
                    Formatting.RED + "Base Value: " + getCalculatedBaseValue(ctx.calcData.level)));
            }
        }

        return list;
    }

    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();

        if (!empty) {
            getAllScalingValues().forEach(x -> list.addAll(x.GetTooltipString(info)));

            if (base_val > 0) {
                list.add(new LiteralText(
                    Formatting.RED + "Base Value: " + getCalculatedBaseValue(info.unitdata.getLevel())));
            }
        }

        return list;
    }

}
