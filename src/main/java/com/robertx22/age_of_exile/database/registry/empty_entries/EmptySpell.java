package com.robertx22.age_of_exile.database.registry.empty_entries;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
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
import net.minecraft.text.Text;

import java.util.List;

public class EmptySpell extends BaseSpell {

    public EmptySpell() {
        super(new ImmutableSpellConfigs() {

            @Override
            public SpellCastType castType() {
                return null;
            }

            @Override
            public SoundEvent sound() {
                return null;
            }

            @Override
            public Elements element() {
                return null;
            }
        });
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.CAST_TIME_TICKS, 0, 0);
        return p;
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {
        return null;
    }

    @Override
    public Words getName() {
        return Words.Empty;
    }

}
