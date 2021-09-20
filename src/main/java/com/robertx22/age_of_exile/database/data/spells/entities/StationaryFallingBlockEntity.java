package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;

public class StationaryFallingBlockEntity extends FallingBlockEntity implements IDatapackSpellEntity {

    public StationaryFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(ModRegistry.ENTITIES.SIMPLE_BLOCK_ENTITY, world);
    }

    public StationaryFallingBlockEntity(World world, BlockPos pos, BlockState block) {
        this(ModRegistry.ENTITIES.SIMPLE_BLOCK_ENTITY, world);
        FallingBlockAccessor acc = (FallingBlockAccessor) this;
        acc.setBlockState(block);
        acc.setDestroyedOnLanding(false);
        this.blocksBuilding = true;
        this.setPos(pos.getX() + 0.5D, pos.getY() + (double) ((1.0F - this.getBbHeight()) / 2.0F), pos.getZ() + 0.5D);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = pos.getX();
        this.yo = pos.getY();
        this.zo = pos.getZ();
        this.setStartPos(this.blockPosition());
    }

    @Override
    public BlockState getBlockState() {

        try {
            return Registry.BLOCK.get(new ResourceLocation(this.entityData.get(BLOCK)))
                .defaultBlockState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getBlockState();
    }

    int lifespan = 1000;

    EntitySavedSpellData spellData;

    private static final DataParameter<CompoundNBT> SPELL_DATA = EntityDataManager.defineId(StationaryFallingBlockEntity.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<String> ENTITY_NAME = EntityDataManager.defineId(StationaryFallingBlockEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> BLOCK = EntityDataManager.defineId(StationaryFallingBlockEntity.class, DataSerializers.STRING);
    public static final DataParameter<Boolean> IS_FALLING = EntityDataManager.defineId(StationaryFallingBlockEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Float> FALL_SPEED = EntityDataManager.defineId(StationaryFallingBlockEntity.class, DataSerializers.FLOAT);

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    @Override
    public void tick() {

        if (this.removeNextTick) {
            this.remove();
            return;
        }

        //this.age++; this is called somewhere again idk

        if (entityData.get(IS_FALLING)) {
            if (!this.isNoGravity()) {

                float speed = entityData.get(FALL_SPEED);
                speed *= 1 + 0.03F * tickCount;

                this.setDeltaMovement(this.getDeltaMovement()
                    .add(0.0D, speed, 0.0D));
            }
            this.move(MoverType.SELF, this.getDeltaMovement());

            if (this.onGround) {
                remove();
            }

        }

        try {
            this.getSpellData()
                .getSpell()
                .getAttached()
                .tryActivate(getScoreboardName(), SpellCtx.onTick(getSpellData().getCaster(level), this, getSpellData()));
        } catch (Exception e) {
            e.printStackTrace();
            this.remove();
        }

        if (tickCount > lifespan) {
            remove();
        }
    }

    @Override
    public void remove() {

        try {
            if (getSpellData() != null) {
                LivingEntity caster = getSpellData().getCaster(level);

                if (caster != null) {
                    this.getSpellData()
                        .getSpell()
                        .getAttached()
                        .tryActivate(getScoreboardName(), SpellCtx.onExpire(caster, this, getSpellData()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.remove();
    }

    static Gson GSON = new Gson();

    public EntitySavedSpellData getSpellData() {
        if (level.isClientSide) {
            if (spellData == null) {
                CompoundNBT nbt = entityData.get(SPELL_DATA);
                if (nbt != null) {
                    this.spellData = GSON.fromJson(nbt.getString("spell"), EntitySavedSpellData.class);
                }
            }
        }
        return spellData;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPELL_DATA, new CompoundNBT());
        this.entityData.define(ENTITY_NAME, "");
        this.entityData.define(BLOCK, "");
        this.entityData.define(IS_FALLING, false);
        this.entityData.define(FALL_SPEED, -0.04F);
        super.defineSynchedData();
    }

    public String getScoreboardName() {
        return entityData.get(ENTITY_NAME);
    }

    @Override
    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.lifespan = holder.get(MapField.LIFESPAN_TICKS)
            .intValue();

        data.item_id = holder.get(MapField.ITEM);
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("spell", GSON.toJson(spellData));
        entityData.set(SPELL_DATA, nbt);
        entityData.set(ENTITY_NAME, holder.get(MapField.ENTITY_NAME));
        entityData.set(BLOCK, holder.get(MapField.BLOCK));
        entityData.set(FALL_SPEED, holder.getOrDefault(MapField.BLOCK_FALL_SPEED, -0.04D)
            .floatValue());

    }
}
