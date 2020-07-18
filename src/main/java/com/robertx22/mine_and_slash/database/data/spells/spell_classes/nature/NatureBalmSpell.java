package com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.RegenerateEffect;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class NatureBalmSpell extends BaseSpell {

    private NatureBalmSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.GIVE_EFFECT;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION;
                }

                @Override
                public Elements element() {
                    return Elements.Nature;
                }
            }.addsEffect(RegenerateEffect.INSTANCE)
                .setSwingArmOnCast());

    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 25, 25);
        c.set(SC.RADIUS, 2, 4);
        c.set(SC.CAST_TIME_TICKS, 30, 20);
        c.set(SC.COOLDOWN_SECONDS, 60, 60);
        c.set(SC.DURATION_TICKS, 60 * 15, 60 * 15);
        c.set(SC.TICK_RATE, 30, 20);
        c.set(SC.BASE_VALUE, 1, 3);

        c.setMaxLevel(14);
        return c;
    }

    public static NatureBalmSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "nature_balm";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("Applies buff: "));
        list.addAll(RegenerateEffect.INSTANCE.GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.NatureBalm;
    }

    @Override
    public void spawnParticles(SpellCastContext ctx) {
        if (ctx.caster.world.isClient) {
            ParticleUtils.spawnParticles(ParticleTypes.HAPPY_VILLAGER, ctx.caster, 10);
        }
    }

    private static class SingletonHolder {
        private static final NatureBalmSpell INSTANCE = new NatureBalmSpell();
    }
}
