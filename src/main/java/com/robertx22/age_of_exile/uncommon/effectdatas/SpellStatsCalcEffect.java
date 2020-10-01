package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
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

    }

    public static class CalculatedSpellConfiguration {

        public int extraProjectiles = 0;

        private HashMap<SpellModEnum, Float> map = new HashMap<>();

        public CalculatedSpellConfiguration() {
            for (SpellModEnum value : SpellModEnum.values()) {
                map.put(value, 100F);
            }
        }

        public void add(SpellModEnum en, StatData data) {
            map.put(en, map.get(en) + data.getAverageValue());
        }

        public void add(SpellModEnum mod, float val) {
            map.put(mod, map.get(mod) + val);
        }

        public float getMulti(SpellModEnum mod) {
            return map.get(mod) / 100F;
        }

    }

}
