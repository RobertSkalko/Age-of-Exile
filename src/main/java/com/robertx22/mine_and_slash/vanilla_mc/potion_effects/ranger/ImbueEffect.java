package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.hunting.ImbueSpell;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
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
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.BASE_VALUE, 1, 3);
        p.set(SC.ATTACK_SCALE_VALUE, 0.05F, 0.2F);
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return ImbueSpell.getInstance();
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        list.add(new SText(Formatting.GREEN + "Adds damage to Ranger spells."));

        list.addAll(getCalc(info.player)
            .GetTooltipString(info, Load.spells(info.player), this));

        return list;

    }

    private static class SingletonHolder {
        private static final ImbueEffect INSTANCE = new ImbueEffect();
    }
}

