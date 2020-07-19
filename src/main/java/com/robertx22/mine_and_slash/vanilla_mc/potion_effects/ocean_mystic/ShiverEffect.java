package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ShiverEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final ShiverEffect INSTANCE = new ShiverEffect();

    private ShiverEffect() {
        super(StatusEffectType.HARMFUL, 4393423);

        this.tickActions.add(new OnTickAction(ctx -> {
            ParticleUtils.spawnParticles(ParticleTypes.DOLPHIN, ctx.entity, 5);
            return ctx;
        }, null));

    }

    @Override
    public String GUID() {
        return "shiver";
    }

    @Override
    public String locNameForLangFile() {
        return "Shiver";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(-2, new ElementalResist(Elements.Water)));
        list.add(new PotionStat(-5, new ElementalResist(Elements.Fire)));
        list.add(new PotionStat(-5, new ElementalResist(Elements.Thunder)));
        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.DURATION_TICKS, 100, 200);
        p.set(SC.TICK_RATE, 20, 20);
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

