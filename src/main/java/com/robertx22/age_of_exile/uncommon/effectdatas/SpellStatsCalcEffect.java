package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class SpellStatsCalcEffect extends EffectData {
    public CalculatedSpellData data;

    public SpellConfiguration config;

    public String spell_id;

    public PlayerSpellCap.ISpellsCap spells;

    public Float cooldown = 100F;
    public Float castTime = 100F;
    public Float manaCost = 100F;

    public SpellStatsCalcEffect(LivingEntity caster, CalculatedSpellData data) {
        super(caster, caster);

        this.spell_id = data.spell_id;
        this.spells = Load.spells(caster);
        this.data = data;
        this.config = data.spell.getConfig();
    }

    @Override
    protected void activate() {
        this.config.cast_time_ticks *= castTime / 100F;
        this.config.cooldown_ticks *= cooldown / 100F;
        this.config.mana_cost *= manaCost / 100F;
    }
}
