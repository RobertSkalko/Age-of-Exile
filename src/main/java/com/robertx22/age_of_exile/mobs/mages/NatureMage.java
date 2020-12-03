package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.aoe_data.database.spells.IntSpells;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class NatureMage extends BaseMage {

    public NatureMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Spell getSpell() {
        return Database.Spells()
            .get(IntSpells.POISONBALL_ID);
    }
}

