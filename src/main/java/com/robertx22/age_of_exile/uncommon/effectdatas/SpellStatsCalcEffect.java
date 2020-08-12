package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import net.minecraft.entity.LivingEntity;

public class SpellStatsCalcEffect extends EffectData {
    public PreCalcSpellConfigs configs;

    public PlayerSpellCap.ISpellsCap spells;
    public SpellCastContext ctx;

    public SpellStatsCalcEffect(SpellCastContext ctx, PreCalcSpellConfigs configs, LivingEntity source, LivingEntity target) {
        super(source, target);

        this.ctx = ctx;
        this.spells = ctx.spellsCap;
        this.configs = configs;
    }

    @Override
    protected void activate() {

    }
}
