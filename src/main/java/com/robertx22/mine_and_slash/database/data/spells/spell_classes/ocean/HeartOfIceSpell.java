package com.robertx22.mine_and_slash.database.data.spells.spell_classes.ocean;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.List;

public class HeartOfIceSpell extends BaseSpell {

    private HeartOfIceSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.SELF_HEAL;
                }

                @Override
                public SoundEvent sound() {
                    return ModRegistry.SOUNDS.FREEZE;
                }

                @Override
                public Elements element() {
                    return Elements.Water;
                }
            }.setSwingArmOnCast());
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 15, 25);
        c.set(SC.BASE_VALUE, 25, 25);
        c.set(SC.CAST_TIME_TICKS, 30, 15);
        c.set(SC.COOLDOWN_SECONDS, 60, 30);

        c.setMaxLevel(12);

        return c;
    }

    public static HeartOfIceSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "heart_of_ice";
    }

    @Override
    public List<MutableText> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<MutableText> list = new ArrayList<>();

        list.add(new LiteralText("Restores health to caster:"));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.HeartOfIce;
    }

    @Override
    public void castExtra(SpellCastContext ctx) {
        try {
            //SoundUtils.playSound(ctx.caster, ModSounds.FREEZE.get(), 1, 1);
            ParticleUtils.spawnParticles(ModRegistry.PARTICLES.BUBBLE, ctx.caster, 25);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class SingletonHolder {
        private static final HeartOfIceSpell INSTANCE = new HeartOfIceSpell();
    }
}
