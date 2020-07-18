package com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.WizardryEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;

public class WizardrySpell extends BaseDivineBuffSpell {
    private WizardrySpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return SpellCastType.AOE_EFFECT;
            }

            @Override
            public SoundEvent sound() {
                return SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
            }

            @Override
            public Elements element() {
                return Elements.Elemental;
            }
        }.addsEffect(WizardryEffect.INSTANCE));
    }

    public static WizardrySpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "wizardry";
    }

    @Override
    public Words getName() {
        return Words.Wizardry;
    }

    private static class SingletonHolder {
        private static final WizardrySpell INSTANCE = new WizardrySpell();
    }
}
