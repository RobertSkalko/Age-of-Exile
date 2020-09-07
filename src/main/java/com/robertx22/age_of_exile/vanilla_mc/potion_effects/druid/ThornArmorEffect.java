package com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid;

import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ThornArmorEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final ThornArmorEffect INSTANCE = new ThornArmorEffect();

    private ThornArmorEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    @Override
    public String GUID() {
        return "thorn_armor";
    }

    @Override
    public String locNameForLangFile() {
        return "Thorn Armor";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(25, new ElementalResist(Elements.Nature)));
        list.add(new PotionStat(50, Armor.getInstance()));
        return list;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        return list;

    }

    @Override
    public ValueCalculationData getCalc(LivingEntity caster) {
        return ValueCalculationData.base(0);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 10;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10000;
    }

}

