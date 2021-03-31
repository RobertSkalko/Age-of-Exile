package com.robertx22.age_of_exile.saveclasses.spells.calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
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
        data.attack_scaling = attack;
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

    public List<BaseStatCalc> getAllScalingValues() {
        List<BaseStatCalc> list = new ArrayList<>();
        list.addAll(scaling_values);
        return list;
    }

    @Store
    public List<ScalingStatCalc> scaling_values = new ArrayList<>();

    @Store
    public StatScaling base_scaling_type = StatScaling.NORMAL;
    @Store
    public StatScaling atk_scaling_type = StatScaling.DOUBLE_AT_MAX_LVL;

    @Store
    public float attack_scaling = 0;

    private transient boolean empty = false;

    @Store
    public float base_val = 0;

    public int getCalculatedBaseValue(int lvl) {

        if (base_scaling_type == null) {
            MMORPG.logError("base scaling null");
            return 0;
        }

        return (int) base_scaling_type.scale(base_val, lvl);
    }

    private int getCalculatedScalingValue(EntityCap.UnitData data, int lvl) {

        float amount = 0;
        if (attack_scaling > 0) {
            for (Stat stat : new AttackDamage(Elements.Nature).generateAllPossibleStatVariations()) {
                amount += data.getUnit()
                        .getCalculatedStat(stat.GUID())
                        .getAverageValue() * this.atk_scaling_type.scale(attack_scaling, lvl);
            }
        }
        amount += getAllScalingValues().stream()
                .mapToInt(x -> x.getCalculatedValue(data))
                .sum();

        return (int) amount;
    }

    public int getCalculatedValue(LivingEntity caster, EntitySavedSpellData ctx) {
        int val = getCalculatedScalingValue(Load.Unit(caster), ctx.lvl);
        val += getCalculatedBaseValue(ctx.lvl);

        return val;
    }

    public Text getShortTooltip(int lvl) {
        MutableText text = new LiteralText("");

        if (this.base_val > 0) {
            text.append(getCalculatedBaseValue(lvl) + "");
        }

        if (attack_scaling > 0) {
            text.append(" + " + (int) (this.atk_scaling_type.scale(attack_scaling, lvl) * 100) + "% Weapon Attack");
        }

        return text;

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
