package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModStatData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;

public class SpellStatsCalcEffect extends EffectData {
    public CalculatedSpellConfiguration data = new CalculatedSpellConfiguration();

    public String spell_id;

    public PlayerSpellCap.ISpellsCap spells;

    public SpellStatsCalcEffect(LivingEntity caster, String spell) {
        super(caster, caster);
        this.spell_id = spell;
        this.spells = Load.spells(caster);

    }

    @Override
    protected void activate() {
        Spell spell = SlashRegistry.Spells()
            .get(spell_id);
        Load.perks(this.source)
            .getAllSpellModifiersFor(spell)
            .forEach(m -> m.mods.forEach(e -> data.add(e)));

    }

    public static class CalculatedSpellConfiguration {

        private HashMap<SpellModEnum, Float> map = new HashMap<>();

        private CalculatedSpellConfiguration() {
            for (SpellModEnum value : SpellModEnum.values()) {
                map.put(value, 100F);
            }
        }

        public void add(SpellModEnum mod, float val) {
            map.put(mod, map.get(mod) + val);
        }

        public void add(SpellModStatData data) {
            map.put(data.spell_stat, map.get(data.spell_stat) + data.value);
        }

        public float getMulti(SpellModEnum mod) {
            return map.get(mod) / 100F;
        }

    }

}
