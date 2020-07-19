package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ColdEssenceEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final ColdEssenceEffect INSTANCE = new ColdEssenceEffect();

    private ColdEssenceEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    @Override
    public String GUID() {
        return "frost_essence";
    }

    @Override
    public String locNameForLangFile() {
        return "Cold Essence";
    }

    @Override
    public int getMaxStacks() {
        return 20;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(1F, CriticalDamage.getInstance()));
        list.add(new PotionStat(0.5F, new ElementalDamageBonus(Elements.Water)));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.setDurationInSeconds(30, 50);
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return null;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        return list;

    }

}

