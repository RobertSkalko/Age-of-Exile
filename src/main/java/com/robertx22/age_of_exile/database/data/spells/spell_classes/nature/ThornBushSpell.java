package com.robertx22.age_of_exile.database.data.spells.spell_classes.nature;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.entities.proj.SeedEntity;
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
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ThornBushSpell extends BaseSpell {

    private ThornBushSpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return SpellCastType.PROJECTILE;
            }

            @Override
            public SoundEvent sound() {
                return SoundEvents.ENTITY_EGG_THROW;
            }

            @Override
            public Elements element() {
                return Elements.Nature;
            }
        }.spawnBlock(ModRegistry.BLOCKS.THORN_BUSH)
            .summonsEntity((world) -> new SeedEntity(world))
            .setSwingArmOnCast());

        this.onDamageEffects.add(new EffectChance(PoisonEffect.INSTANCE, 25, IStatEffect.EffectSides.Target));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    public static ThornBushSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 20, 40);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.SHOOT_SPEED, 0.4F, 0.6F);
        c.set(SC.BASE_VALUE, 3, 12);
        c.set(SC.CAST_TIME_TICKS, 0, 0);
        c.set(SC.COOLDOWN_SECONDS, 50, 30);
        c.set(SC.TICK_RATE, 30, 30);
        c.set(SC.RADIUS, 1.5F, 3);
        c.set(SC.DURATION_TICKS, 100, 150);

        c.setMaxLevel(12);
        return c;
    }

    @Override
    public String GUID() {
        return "thorn_bush";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new SText("Summons a bush."));
        list.add(new SText("That attacks enemies nearby:"));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.ThornBush;
    }

    private static class SingletonHolder {
        private static final ThornBushSpell INSTANCE = new ThornBushSpell();
    }
}
