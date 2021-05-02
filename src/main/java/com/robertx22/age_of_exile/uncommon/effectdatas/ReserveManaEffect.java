package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

public class ReserveManaEffect extends EffectData {
    public static String ID = "on_reserve_mana";

    @Override
    public String GUID() {
        return ID;
    }

    public ReserveManaEffect(Spell spell, LivingEntity source) {
        super(spell.aura_data.mana_reserved, source, source);
        this.data.setString(EventData.SPELL, spell.GUID());
    }

    @Override
    protected void activate() {
    }

}
