package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
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
