package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class StationaryFallingBlockEntity extends FallingBlockEntity implements IDatapackSpellEntity {

    public StationaryFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public StationaryFallingBlockEntity(World world, BlockPos pos, BlockState block) {
        this(ModRegistry.ENTITIES.SIMPLE_BLOCK_ENTITY, world);
        FallingBlockAccessor acc = (FallingBlockAccessor) this;
        acc.setBlockState(block);
        acc.setDestroyedOnLanding(false);
        this.inanimate = true;
        this.updatePosition(pos.getX(), pos.getY() + (double) ((1.0F - this.getHeight()) / 2.0F), pos.getZ());
        this.setVelocity(Vec3d.ZERO);
        this.prevX = pos.getX();
        this.prevY = pos.getY();
        this.prevZ = pos.getZ();
        this.setFallingBlockPos(this.getBlockPos());
    }

    int lifespan = 1000;

    EntitySavedSpellData spellData;

    private static final TrackedData<CompoundTag> SPELL_DATA = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<String> ENTITY_NAME = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.STRING);

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<>();
    }

    public void tick() {
        this.age++;

        try {
            this.getSpellData().attached.onEntityTick(getEntityName(), SpellCtx.onTick(getSpellData().getCaster(world), this, getSpellData()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (age > lifespan) {
            remove();
        }
    }

    static Gson GSON = new Gson();

    public EntitySavedSpellData getSpellData() {
        if (world.isClient) {
            if (spellData == null) {
                CompoundTag nbt = dataTracker.get(SPELL_DATA);
                if (nbt != null) {
                    this.spellData = GSON.fromJson(nbt.getString("spell"), EntitySavedSpellData.class);
                }
            }
        }
        return spellData;
    }

    public String getEntityName() {
        return dataTracker.get(ENTITY_NAME);
    }

    @Override
    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.lifespan = holder.get(MapField.LIFESPAN_TICKS)
            .intValue();

        data.item_id = holder.get(MapField.ITEM);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("spell", GSON.toJson(spellData));
        dataTracker.set(SPELL_DATA, nbt);
        dataTracker.set(ENTITY_NAME, holder.get(MapField.ENTITY_NAME));

    }
}
