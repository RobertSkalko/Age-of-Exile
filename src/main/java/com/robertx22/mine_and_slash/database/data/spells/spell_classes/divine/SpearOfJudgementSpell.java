package com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.entities.trident.SpearOfJudgementEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SpearOfJudgementSpell extends BaseSpell {

    private SpearOfJudgementSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.PROJECTILE;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.BLOCK_FIRE_EXTINGUISH;
                }

                @Override
                public Elements element() {
                    return Elements.Thunder;
                }
            }.summonsEntity(x -> new SpearOfJudgementEntity(x))
                .setSwingArmOnCast()
        );
    }

    public static SpearOfJudgementSpell getInstance() {
        return SpearOfJudgementSpell.SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 10, 25);
        c.set(SC.BASE_VALUE, 10, 40);
        c.set(SC.CAST_TIME_TICKS, 0, 0);
        c.set(SC.COOLDOWN_SECONDS, 120, 60);
        c.set(SC.TIMES_TO_CAST, 1, 1);
        c.set(SC.SHOOT_SPEED, 0.9F, 1.5F);
        c.set(SC.DURATION_TICKS, 80, 100);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.TICK_RATE, 10, 10);

        c.setMaxLevel(16);

        return c;
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.STR;
    }

    @Override
    public String GUID() {
        return "spear_of_judgement";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Throw out a spear that deals damage and"));
        list.add(new LiteralText("applies Judgement: "));

        list.addAll(JudgementEffect.INSTANCE.GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.SpearOfJudgement;
    }

    private static class SingletonHolder {
        private static final SpearOfJudgementSpell INSTANCE = new SpearOfJudgementSpell();
    }
}