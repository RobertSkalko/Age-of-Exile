package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine;

import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealthRegen;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IOneOfATypePotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BraveryEffect extends BasePotionEffect implements IApplyStatPotion, IOneOfATypePotion {
    @Override
    public IOneOfATypePotion.Type getOneOfATypeType() {
        return IOneOfATypePotion.Type.DIVINE_BUFF;
    }

    public static final BraveryEffect INSTANCE = new BraveryEffect();

    private BraveryEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    @Override
    public String GUID() {
        return "bravery";
    }

    @Override
    public String locNameForLangFile() {
        return "Bravery";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(50, Armor.getInstance()));
        list.add(new PotionStat(2, HealthRegen.getInstance()));
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
        return 60;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10000;
    }

}

