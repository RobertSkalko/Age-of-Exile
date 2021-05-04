package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

public class ReserveManaEvent extends EffectEvent {

    public static String ID = "on_reserve_mana";

    @Override
    public String GUID() {
        return ID;
    }

    public ReserveManaEvent(Spell spell, LivingEntity source) {
        super(spell.aura_data.mana_reserved, source, source);
        this.data.setString(EventData.SPELL, spell.GUID());
    }

    @Override
    protected void activate() {
    }

}
