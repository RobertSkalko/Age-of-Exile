package com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IOneOfATypePotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class WizardryEffect extends BasePotionEffect implements IApplyStatPotion, IOneOfATypePotion {

    @Override
    public IOneOfATypePotion.Type getOneOfATypeType() {
        return IOneOfATypePotion.Type.DIVINE_BUFF;
    }

    public static final WizardryEffect INSTANCE = new WizardryEffect();

    private WizardryEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    @Override
    public String GUID() {
        return "wizardry";
    }

    @Override
    public String locNameForLangFile() {
        return "Wizardry";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(20, new ElementalSpellDamage(Elements.Elemental)));
        list.add(new PotionStat(3, ManaRegen.getInstance()));
        return list;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        return list;
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

}