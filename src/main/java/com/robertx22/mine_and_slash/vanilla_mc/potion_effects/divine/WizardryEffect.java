package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs.WizardrySpell;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaRegen;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IOneOfATypePotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.MutableText;

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
        list.add(new PotionStat(15, new ElementalSpellDamage(Elements.Elemental)));
        list.add(new PotionStat(5, ManaRegen.getInstance()));
        list.add(new PotionStat(10, Intelligence.INSTANCE));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return WizardrySpell.getInstance();
    }

    @Override
    public List<MutableText> getEffectTooltip(TooltipInfo info) {
        List<MutableText> list = new ArrayList<>();
        return list;
    }

}