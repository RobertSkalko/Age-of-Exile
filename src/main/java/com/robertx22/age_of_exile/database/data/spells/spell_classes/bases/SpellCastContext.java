package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

import java.util.Objects;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityCap.UnitData data;
    public final EntitySpellCap.ISpellsCap spellsCap;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public SpellStatsCalculationEvent event;
    public EntitySavedSpellData calcData;

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;
        this.spell = spell;
        this.data = Load.Unit(caster);

        Objects.requireNonNull(spell);

        this.spellsCap = Load.spells(caster);

        this.calcData = EntitySavedSpellData.create(data.getLevel(), caster, spell);

        this.event = new SpellStatsCalculationEvent(this.calcData, caster, spell.GUID());
        event.Activate();

        int castTicks = (int) event.data.getNumber(EventData.CAST_TICKS).number;
        this.isLastCastTick = castTicks == ticksInUse;

    }
}
