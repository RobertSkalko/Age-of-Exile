package com.robertx22.age_of_exile.database.data.spells.entities;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import net.minecraft.entity.LivingEntity;

public interface IDatapackSpellEntity {

    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder);

}
