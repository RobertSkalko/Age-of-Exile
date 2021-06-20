package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
        this.inanimate = true;
        this.setPosition(pos.getX() + 0.5D, pos.getY() + (double) ((1.0F - this.getHeight()) / 2.0F), pos.getZ() + 0.5D);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = pos.getX();
        this.prevY = pos.getY();
        this.prevZ = pos.getZ();
        this.setFallingBlockPos(this.getBlockPos());
    }

    @Override
    public BlockState getBlockState() {

        try {
            return Registry.BLOCK.get(new Identifier(this.dataTracker.get(BLOCK)))
                .getDefaultState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getBlockState();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    int lifespan = 1000;

    EntitySavedSpellData spellData;

    private static final TrackedData<NbtCompound> SPELL_DATA = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<String> ENTITY_NAME = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> BLOCK = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<Boolean> IS_FALLING = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Float> FALL_SPEED = DataTracker.registerData(StationaryFallingBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Override
    public Iterable<ItemStack> getArmorItems() {
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

        if (dataTracker.get(IS_FALLING)) {
            if (!this.hasNoGravity()) {

                float speed = dataTracker.get(FALL_SPEED);
                speed *= 1 + 0.03F * age;

                this.setVelocity(this.getVelocity()
                    .add(0.0D, speed, 0.0D));
            }
            this.move(MovementType.SELF, this.getVelocity());

            if (this.onGround) {
                remove();
            }

        }

        try {
            this.getSpellData()
                .getSpell()
                .getAttached()
                .tryActivate(getEntityName(), SpellCtx.onTick(getSpellData().getCaster(world), this, getSpellData()));
        } catch (Exception e) {
            e.printStackTrace();
            this.remove();
        }

        if (age > lifespan) {
            remove();
        }
    }

    @Override
    public void remove() {

        try {
            if (getSpellData() != null) {
                LivingEntity caster = getSpellData().getCaster(world);

                if (caster != null) {
                    this.getSpellData()
                        .getSpell()
                        .getAttached()
                        .tryActivate(getEntityName(), SpellCtx.onExpire(caster, this, getSpellData()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.remove();
    }

    static Gson GSON = new Gson();

    public EntitySavedSpellData getSpellData() {
        if (world.isClient) {
            if (spellData == null) {
                NbtCompound nbt = dataTracker.get(SPELL_DATA);
                if (nbt != null) {
                    this.spellData = GSON.fromJson(nbt.getString("spell"), EntitySavedSpellData.class);
                }
            }
        }
        return spellData;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPELL_DATA, new NbtCompound());
        this.dataTracker.startTracking(ENTITY_NAME, "");
        this.dataTracker.startTracking(BLOCK, "");
        this.dataTracker.startTracking(IS_FALLING, false);
        this.dataTracker.startTracking(FALL_SPEED, -0.04F);
        super.initDataTracker();
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
        NbtCompound nbt = new NbtCompound();
        nbt.putString("spell", GSON.toJson(spellData));
        dataTracker.set(SPELL_DATA, nbt);
        dataTracker.set(ENTITY_NAME, holder.get(MapField.ENTITY_NAME));
        dataTracker.set(BLOCK, holder.get(MapField.BLOCK));
        dataTracker.set(FALL_SPEED, holder.getOrDefault(MapField.BLOCK_FALL_SPEED, -0.04D)
            .floatValue());

    }
}
