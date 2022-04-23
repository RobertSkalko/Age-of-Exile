package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

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

    public String id = "";

    public DamageCalcs damage_calcs = new DamageCalcs();

    public List<ScalingCalc> stat_scalings = new ArrayList<>();
    public LeveledValue attack_scaling = new LeveledValue(0, 0);
    public LeveledValue base = new LeveledValue(0, 0);

    public int getCalculatedBaseValue(LevelProvider provider) {
        return (int) StatScaling.NORMAL.scale(base.getValue(provider), provider.getCasterLevel());
    }

    public String getLocDmgTooltip(Elements element) {
        return "[calc:" + id + "]" + " " + element.getIconNameDmg();
    }

    public String getLocDmgTooltip() {
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

    public ITextComponent getShortTooltip(LevelProvider provider) {
        IFormattableTextComponent text = new StringTextComponent("");

        int val = getCalculatedValue(provider);

        if (this.base.getValue(provider) > 0) {
            text.append(val + "");
        }

        if (attack_scaling.getValue(provider) > 0) {
            if (val > 0) {
                text.append(val + "");
            }

            if (val < 1 || Screen.hasShiftDown()) {
                text.append(" (" + (int) (attack_scaling.getValue(provider) * 100) + "% Weapon Damage)")
                    .withStyle(TextFormatting.YELLOW);
            }
        }

        stat_scalings.forEach(x -> {
            text.append(getCalculatedValue(provider) + "");

            text.append(" ")
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
