package com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ImbueEffect extends BasePotionEffect {

    private ImbueEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    public static ImbueEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "imbue";
    }

    @Override
    public String locNameForLangFile() {
        return "Imbue";
    }

    @Override
    public int getMaxStacks() {
        return 5;
    }

    @Override
    public SpellCalcData getCalc(LivingEntity caster) {
        return SpellCalcData.base(0);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 30;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10000;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        list.add(new SText(Formatting.GREEN + "Increases ranger spells damage by 50%"));

        list.addAll(getCalc(info.player)
            .GetTooltipString(info));

        return list;

    }

    private static class SingletonHolder {
        private static final ImbueEffect INSTANCE = new ImbueEffect();
    }
}

