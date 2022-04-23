package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import net.minecraft.entity.LivingEntity;

import java.util.Objects;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityData data;
    public final EntitySpellCap.ISpellsCap spellsCap;
    public final Spell spell;
    public SpellStatsCalculationEvent event;
    public EntitySavedSpellData calcData;

    public SpellCastContext(LivingEntity caster, Spell spell) {
        this.caster = caster;
        this.spell = spell;
        this.data = Load.Unit(caster);

        Objects.requireNonNull(spell);

        this.spellsCap = Load.spells(caster);

        this.calcData = EntitySavedSpellData.create(data.getLevel(), caster, spell);

        this.event = new SpellStatsCalculationEvent(this.calcData, caster, spell.GUID());
        event.Activate();

    }
}
