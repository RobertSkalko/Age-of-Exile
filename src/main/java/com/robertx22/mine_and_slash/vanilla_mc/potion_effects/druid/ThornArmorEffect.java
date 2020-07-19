package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature.ThornArmorSpell;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.MutableText;

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
        list.add(new PotionStat(10, new ElementalResist(Elements.Nature)));
        list.add(new PotionStat(30, Armor.getInstance()));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return ThornArmorSpell.getInstance();
    }

    @Override
    public List<MutableText> getEffectTooltip(TooltipInfo info) {
        List<MutableText> list = new ArrayList<>();

        return list;

    }

}

