package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import net.minecraft.entity.LivingEntity;

public interface IDatapackSpellEntity {
    public void init(LivingEntity caster, EntitySavedSpellData data);

}
