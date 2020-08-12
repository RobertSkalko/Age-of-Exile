package com.robertx22.age_of_exile.database.data.spells.spell_classes.nature;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonedWeaponsEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PoisonedWeaponsSpell extends BaseSpell {

    private PoisonedWeaponsSpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return SpellCastType.GIVE_EFFECT;
            }

            @Override
            public SoundEvent sound() {
                return SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
            }

            @Override
            public Elements element() {
                return Elements.Nature;
            }
        }.addsEffect(PoisonedWeaponsEffect.getInstance())
            .setSwingArmOnCast()
            .castingWeapon(CastingWeapon.ANY_WEAPON));

    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.STR;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 20, 40);
        c.set(SC.CAST_TIME_TICKS, 30, 15);
        c.set(SC.COOLDOWN_SECONDS, 120, 60);

        c.setMaxLevel(12);
        return c;
    }

    public static PoisonedWeaponsSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "poisoned_weapons";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Applies buff: "));
        list.addAll(PoisonedWeaponsEffect.getInstance()
            .GetTooltipStringWithNoExtraSpellInfo(info));

        return list;

    }

    @Override
    public Words getName() {
        return Words.PoisonedWeapons;
    }

    private static class SingletonHolder {
        private static final PoisonedWeaponsSpell INSTANCE = new PoisonedWeaponsSpell();
    }
}
