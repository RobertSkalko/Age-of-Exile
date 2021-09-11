package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ValueCalculation implements JsonExileRegistry<ValueCalculation>, IAutoGson<ValueCalculation> {

    public static ValueCalculation SERIALIZER = new ValueCalculation();

    @Factory
    public ValueCalculation() {

    }

    public List<ScalingCalc> getAllScalingValues() {
        return new ArrayList<>(stat_scalings);
    }

    public List<ScalingCalc> stat_scalings = new ArrayList<>();

    public String id = "";
    public StatScaling base_scaling_type = StatScaling.NORMAL;
    public LeveledValue attack_scaling = new LeveledValue(0, 0);
    public LeveledValue base = new LeveledValue(0, 0);

    public int getCalculatedBaseValue(LevelProvider provider) {

        if (base_scaling_type == null) {
            MMORPG.logError("base scaling type null");
            return 0;
        }

        return (int) base_scaling_type.scale(base.getValue(provider), provider.getCasterLevel());
    }

    public String getLocSpellTooltip() {
        return "[calc:" + id + "]";
    }

    private int getCalculatedScalingValue(LevelProvider provider) {

        float amount = 0;
        if (attack_scaling.getValue(provider) > 0) {
            for (Stat stat : new AttackDamage(Elements.Earth).generateAllPossibleStatVariations()) {
                amount += provider.getCasterData()
                    .getUnit()
                    .getCalculatedStat(stat.GUID())
                    .getValue() * attack_scaling.getValue(provider);
            }
        }
        amount += getAllScalingValues().stream()
            .mapToInt(x -> x.getCalculatedValue(provider))
            .sum();

        return (int) amount;
    }

    public int getCalculatedValue(LevelProvider provider) {
        int val = getCalculatedScalingValue(provider);
        val += getCalculatedBaseValue(provider);
        return val;

    }

    public Text getShortTooltip(LevelProvider provider) {
        MutableText text = new LiteralText("");

        if (this.base.getValue(provider) > 0) {
            text.append(getCalculatedBaseValue(provider) + "");
        }

        if (attack_scaling.getValue(provider) > 0) {
            text.append(" + " + (int) (attack_scaling.getValue(provider) * 100) + "% Weapon Attack");
        }

        stat_scalings.forEach(x -> {
            text.append(" + ")
                .append(x.GetTooltipString(provider));
        });

        return text;

    }

    @Override
    public Class<ValueCalculation> getClassForSerialization() {
        return ValueCalculation.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.VALUE_CALC;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
