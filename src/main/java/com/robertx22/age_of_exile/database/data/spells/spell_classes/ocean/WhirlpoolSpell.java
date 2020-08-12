package com.robertx22.age_of_exile.database.data.spells.spell_classes.ocean;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.entities.proj.WhirlpoolEntity;
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
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic.ShiverEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class WhirlpoolSpell extends BaseSpell {

    private WhirlpoolSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.PROJECTILE;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE;
                }

                @Override
                public Elements element() {
                    return Elements.Water;
                }
            }.summonsEntity(w -> new WhirlpoolEntity(w))
                .setSwingArmOnCast());

        this.onDamageEffects.add(new EffectChance(ShiverEffect.INSTANCE, 10, IStatEffect.EffectSides.Target));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 15, 30);
        c.set(SC.BASE_VALUE, 4, 10);
        c.set(SC.SHOOT_SPEED, 0.6F, 0.9F);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.CAST_TIME_TICKS, 30, 20);
        c.set(SC.COOLDOWN_SECONDS, 100, 60);
        c.set(SC.TICK_RATE, 30, 20);
        c.set(SC.RADIUS, 3.5F, 4);
        c.set(SC.DURATION_TICKS, 80, 100);

        c.setMaxLevel(12);

        return c;
    }

    public static WhirlpoolSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "whirlpool";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Summons a whirpool."));
        list.add(new LiteralText("Slows and damages enemies: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.Whirpool;
    }

    private static class SingletonHolder {
        private static final WhirlpoolSpell INSTANCE = new WhirlpoolSpell();
    }
}
