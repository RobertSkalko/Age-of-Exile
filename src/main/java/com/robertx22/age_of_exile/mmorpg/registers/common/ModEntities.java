package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.entities.SimpleArrowEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleTridentEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class ModEntities {

    public List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public ModEntities() {

    }

    public EntityType<SimpleProjectileEntity> SIMPLE_PROJECTILE = projectile(SimpleProjectileEntity::new, "spell_projectile");
    public EntityType<SimpleArrowEntity> SIMPLE_ARROW = projectile(SimpleArrowEntity::new, "spell_arrow");
    public EntityType<StationaryFallingBlockEntity> SIMPLE_BLOCK_ENTITY = projectile(StationaryFallingBlockEntity::new, "spell_block_entity", false);
    public EntityType<SimpleTridentEntity> SIMPLE_TRIDENT = projectile(SimpleTridentEntity::new, "spell_trident", false);

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id) {

        return projectile(factory, id, true);

    }

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, boolean itemRender) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
            .dimensions(new EntityDimensions(0.5F, 0.5F, true))
            .trackedUpdateRate(10)
            .build();

        Registry.register(Registry.ENTITY_TYPE, new Identifier(Ref.MODID, id), type);

        ENTITY_TYPES.add(type);

        if (itemRender) {
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);
        }

        return type;
    }

}


