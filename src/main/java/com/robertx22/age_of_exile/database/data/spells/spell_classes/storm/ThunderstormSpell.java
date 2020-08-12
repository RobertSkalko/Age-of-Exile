package com.robertx22.age_of_exile.database.data.spells.spell_classes.storm;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.ThunderstormEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ThunderstormSpell extends BaseSpell {

    private ThunderstormSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.AT_SIGHT;
                }

                @Override
                public SoundEvent sound() {
                    return null;
                }

                @Override
                public Elements element() {
                    return Elements.Thunder;
                }
            }
                .summonsEntity(world -> new ThunderstormEntity(world))
                .setSwingArmOnCast());
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 30, 50);
        c.set(SC.BASE_VALUE, 5, 20);
        c.set(SC.RADIUS, 2.5F, 4);
        c.set(SC.CAST_TIME_TICKS, 40, 25);
        c.set(SC.COOLDOWN_SECONDS, 60 * 5, 60 * 4);
        c.set(SC.TICK_RATE, 30, 15);
        c.set(SC.DURATION_TICKS, 80, 160);

        c.setMaxLevel(12);

        return c;
    }

    public static ThunderstormSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "thunder_storm";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Summons a cloud of lightning."));
        list.add(new LiteralText("Damaging all enemies inside:"));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.Thunderstorm;
    }

    private static class SingletonHolder {
        private static final ThunderstormSpell INSTANCE = new ThunderstormSpell();
    }
}
