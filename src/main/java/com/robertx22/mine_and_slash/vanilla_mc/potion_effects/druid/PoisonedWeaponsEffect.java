package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature.PoisonedWeaponsSpell;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.List;

public class PoisonedWeaponsEffect extends BasePotionEffect implements IApplyStatPotion {

    private PoisonedWeaponsEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    public static PoisonedWeaponsEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "poisoned_weapons";
    }

    @Override
    public String locNameForLangFile() {
        return "Poisoned Weapons";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(3, new WeaponDamage(Elements.Nature)));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();

        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return PoisonedWeaponsSpell.getInstance();
    }

    @Override
    public List<MutableText> getEffectTooltip(TooltipInfo info) {
        List<MutableText> list = new ArrayList<>();

        return list;

    }

    private static class SingletonHolder {
        private static final PoisonedWeaponsEffect INSTANCE = new PoisonedWeaponsEffect();
    }
}

