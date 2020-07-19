package com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.ThornArmorEffect;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class ThornArmorSpell extends BaseSpell {

    private ThornArmorSpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return SpellCastType.GIVE_EFFECT;
            }

            @Override
            public SoundEvent sound() {
                return SoundEvents.ENTITY_GENERIC_DRINK;
            }

            @Override
            public Elements element() {
                return Elements.Nature;
            }
        }.addsEffect(ThornArmorEffect.INSTANCE)
            .setSwingArmOnCast());
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    public static ThornArmorSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 25, 40);
        c.set(SC.CAST_TIME_TICKS, 30, 20);
        c.set(SC.COOLDOWN_SECONDS, 120, 60);
        c.set(SC.DURATION_TICKS, 60 * 20, 60 * 60);

        c.setMaxLevel(12);
        return c;
    }

    @Override
    public String GUID() {
        return "thorn_armor";
    }

    @Override
    public List<MutableText> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<MutableText> list = new ArrayList<>();

        list.add(new LiteralText("Applies buff: "));

        list.addAll(ThornArmorEffect.INSTANCE.GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.ThornArmor;
    }

    private static class SingletonHolder {
        private static final ThornArmorSpell INSTANCE = new ThornArmorSpell();
    }
}
