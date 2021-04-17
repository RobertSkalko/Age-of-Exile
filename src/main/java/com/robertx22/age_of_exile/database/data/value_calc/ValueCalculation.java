package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.spells.calc.BaseStatCalc;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ValueCalculation implements ISerializedRegistryEntry<ValueCalculation>, IAutoGson<ValueCalculation> {

    public static ValueCalculation SERIALIZER = new ValueCalculation();

    public static ValueCalculation base(String id, float base) {
        ValueCalculation data = new ValueCalculation();
        data.id = id;
        data.base_val = base;
        data.addToSerializables();
        return data;
    }

    public static ValueCalculation scaleWithAttack(String id, float attack, float base) {
        ValueCalculation data = new ValueCalculation();
        data.id = id;
        data.attack_scaling = attack;
        data.base_val = base;
        data.addToSerializables();
        return data;
    }

    public ValueCalculation(String id, ScalingStatCalculation calc, int base) {
        this.stat_scalings.add(calc);
        this.base_val = base;
        this.id = id;
        this.addToSerializables();
    }

    @Factory
    public ValueCalculation() {

    }

    public List<BaseStatCalc> getAllScalingValues() {
        return new ArrayList<>(stat_scalings);
    }

    @Store
    public List<ScalingStatCalculation> stat_scalings = new ArrayList<>();

    @Store
    public String id = "";
    @Store
    public StatScaling base_scaling_type = StatScaling.NORMAL;
    @Store
    public StatScaling atk_scaling_type = StatScaling.DOUBLE_AT_MAX_LVL;
    @Store
    public float attack_scaling = 0;
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

        stat_scalings.forEach(x -> {
            text.append(x.GetTooltipString()); // todo
        });

        return text;

    }

    @Override
    public Class<ValueCalculation> getClassForSerialization() {
        return ValueCalculation.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.VALUE_CALC;
    }

    @Override
    public String GUID() {
        return id;
    }
}
