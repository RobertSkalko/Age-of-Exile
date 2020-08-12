package com.robertx22.age_of_exile.database.data.spells.spell_classes.hunting;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellTooltips;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.ImbueEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ImbueSpell extends BaseSpell {

    private ImbueSpell() {
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
                    return Elements.Elemental;
                }
            }.addsEffect(ImbueEffect.getInstance())
                .castingWeapon(CastingWeapon.RANGED));

    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.DEX;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 10, 25);
        c.set(SC.CAST_TIME_TICKS, 25, 10);
        c.set(SC.COOLDOWN_SECONDS, 45, 25);
        c.set(SC.DURATION_TICKS, 20 * 30, 20 * 45);

        c.setMaxLevel(14);
        return c;
    }

    public static ImbueSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "imbue";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(SpellTooltips.buff());

        list.addAll(ImbueEffect.getInstance()
            .GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.Imbue;
    }

    private static class SingletonHolder {
        private static final ImbueSpell INSTANCE = new ImbueSpell();
    }
}