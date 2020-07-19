package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.List;

public class HunterInstinctEffect extends BasePotionEffect implements IApplyStatPotion {

    private HunterInstinctEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    public static HunterInstinctEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "hunter_instinct";
    }

    @Override
    public String locNameForLangFile() {
        return "Hunter Instinct";
    }

    @Override
    public int getMaxStacks() {
        return 20;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(0.75F, CriticalHit.getInstance()));
        list.add(new PotionStat(10, DodgeRating.getInstance()));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.DURATION_TICKS, 20 * 60, 30 * 60);
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return null;
    }

    @Override
    public List<MutableText> getEffectTooltip(TooltipInfo info) {
        List<MutableText> list = new ArrayList<>();

        return list;

    }

    private static class SingletonHolder {
        private static final HunterInstinctEffect INSTANCE = new HunterInstinctEffect();
    }
}


