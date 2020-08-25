package com.robertx22.age_of_exile.database.data.spells.spell_classes.nature;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.EffectChance;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PetrifyEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GorgonsGazeSpell extends BaseSpell {

    private GorgonsGazeSpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return SpellCastType.SPECIAL;
            }

            @Override
            public SoundEvent sound() {
                return null;
            }

            @Override
            public Elements element() {
                return Elements.Nature;
            }
        }.setSwingArmOnCast());

        this.onDamageEffects.add(new EffectChance(PoisonEffect.INSTANCE, 25, IStatEffect.EffectSides.Target));
    }

    public static GorgonsGazeSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 20, 25);
        c.set(SC.BASE_VALUE, 2, 3);
        c.set(SC.SHOOT_SPEED, 0.4F, 0.6F);
        c.set(SC.CAST_TIME_TICKS, 20, 15);
        c.set(SC.COOLDOWN_SECONDS, 60, 45);
        c.set(SC.DURATION_TICKS, 60, 100);
        c.set(SC.TICK_RATE, 20, 20);

        c.setMaxLevel(16);

        return c;
    }

    @Override
    public String GUID() {
        return "gorgons_gaze";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Turn all enemies before you into stone: "));
        list.add(new LiteralText("Applies debuff: "));
        list.addAll(PetrifyEffect.INSTANCE.GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.GorgonsGaze;
    }

    @Override
    public void castExtra(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        SoundUtils.playSound(caster, ModRegistry.SOUNDS.STONE_CRACK, 1, 1);

        EntityFinder.start(caster, LivingEntity.class, caster.getPos())
            .radius(3)
            .distance(15)
            .finder(EntityFinder.Finder.IN_FRONT)
            .build()
            .forEach(x -> PotionEffectUtils.apply(PetrifyEffect.INSTANCE, caster, x));

    }

    private static class SingletonHolder {
        private static final GorgonsGazeSpell INSTANCE = new GorgonsGazeSpell();
    }
}
