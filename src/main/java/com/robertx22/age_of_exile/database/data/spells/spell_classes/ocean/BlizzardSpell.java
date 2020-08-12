package com.robertx22.age_of_exile.database.data.spells.spell_classes.ocean;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.BlizzardEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.EffectChance;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BlizzardSpell extends BaseSpell {

    private BlizzardSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.AT_SIGHT;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE;
                }

                @Override
                public Elements element() {
                    return Elements.Water;
                }
            }.summonsEntity(w -> new BlizzardEntity(w))
                .setSwingArmOnCast());

        this.onDamageEffects.add(new EffectChance(FrostEffect.INSTANCE, 25, IStatEffect.EffectSides.Target));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    public static BlizzardSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 35, 60);
        c.set(SC.BASE_VALUE, 3, 10);
        c.set(SC.CAST_TIME_TICKS, 50, 30);
        c.set(SC.COOLDOWN_SECONDS, 60 * 5, 60 * 4);
        c.set(SC.TICK_RATE, 30, 15);
        c.set(SC.RADIUS, 3, 4);
        c.set(SC.DURATION_TICKS, 100, 160);

        c.setMaxLevel(12);

        return c;
    }

    @Override
    public String GUID() {
        return "blizzard";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Summon a blizzard."));
        list.add(new LiteralText("Damages all enemies inside."));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.Blizzard;
    }

    private static class SingletonHolder {
        private static final BlizzardSpell INSTANCE = new BlizzardSpell();
    }
}

