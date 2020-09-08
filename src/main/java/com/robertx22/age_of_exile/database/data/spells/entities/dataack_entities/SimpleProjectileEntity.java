package com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.activated_on.Activation;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.IMyRenderAsItem;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SimpleProjectileEntity extends PersistentProjectileEntity implements IMyRenderAsItem, IDatapackSpellEntity {

    EntitySavedSpellData spellData;

    private int xTile;
    private int yTile;
    private int zTile;

    protected boolean inGround;

    private int ticksInGround = 0;
    private int deathTime = 80;

    private static final TrackedData<CompoundTag> SPELL_DATA = DataTracker.registerData(EntityBaseProjectile.class, TrackedDataHandlerRegistry.TAG_COMPOUND);

    public Entity ignoreEntity;

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    protected boolean onExpireProc(LivingEntity caster) {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<>();
    }

    @Override
    public void equipStack(EquipmentSlot slotIn, ItemStack stack) {

    }

    public int getTicksInGround() {
        return this.ticksInGround;
    }

    public int getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(int newVal) {
        this.deathTime = newVal;
    }

    public SimpleProjectileEntity(EntityType<? extends Entity> type, World worldIn) {
        super((EntityType<? extends PersistentProjectileEntity>) type, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
    }

    public LivingEntity getEntityHit(HitResult result, double radius) {

        EntityHitResult enres = null;

        if (result instanceof EntityHitResult) {
            enres = (EntityHitResult) result;
        }

        if (enres != null && enres.getEntity() instanceof LivingEntity) {
            if (!enres.getEntity()
                .isAlive()) {
                return null;
            }
            if (enres.getEntity() != this.getCaster()) {

                return (LivingEntity) enres.getEntity();
            }
        }

        List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, getPos())
            .radius(radius)
            .build();

        if (entities.size() > 0) {

            LivingEntity closest = entities.get(0);

            for (LivingEntity en : entities) {
                if (en != closest) {
                    if (this.distanceTo(en) < this.distanceTo(closest)) {
                        closest = en;
                    }
                }
            }

            if (!closest.isAlive()) {

            }

            return closest;
        }

        return null;

    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        double d0 = this.getBoundingBox()
            .getAverageSideLength() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @Environment(EnvType.CLIENT)
    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(new Vec3d(x, y, z));

        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.yaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
            this.pitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
            this.prevYaw = this.yaw;
            this.prevPitch = this.pitch;
        }
    }

    public void onTick() {
        this.getSpellData().attached.tryActivate(Activation.ON_TICK, SpellCtx.onTick(getCaster(), this, getSpellData()));
    }

    @Override
    public void remove() {

        LivingEntity caster = getCaster();

        if (caster != null) {
            this.getSpellData().attached.tryActivate(Activation.ON_EXPIRE, SpellCtx.onTick(caster, this, getSpellData()));
        }

        super.remove();
    }

    @Override
    public final void tick() {

        if (this.removeNextTick) {
            this.remove();
        }

        try {
            super.tick();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getSpellData() == null || getCaster() == null) {
            if (age > 100) {
                this.scheduleRemoval();
            }
            return;
        }

        try {
            onTick();

            if (this.inGround) {
                ticksInGround++;
            }

            if (this.age >= this.getDeathTime()) {
                onExpireProc(this.getCaster());
                this.scheduleRemoval();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected EntityHitResult getEntityCollision(Vec3d pos, Vec3d posPlusMotion) {

        return ProjectileUtil.getEntityCollision(
            this.world, this, pos, posPlusMotion, this.getBoundingBox()
                .stretch(this.getVelocity())
                .expand(1D), (e) -> {
                return !e.isSpectator() && e.collides() && e instanceof LivingEntity && e != this.getCaster() && e != this.ignoreEntity;
            });

    }

    @Override
    protected void onCollision(HitResult raytraceResultIn) {

        HitResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == HitResult.Type.ENTITY) {
            this.onImpact(raytraceResultIn);
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        } else if (raytraceresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockraytraceresult = (BlockHitResult) raytraceResultIn;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getBlockPos());

            Vec3d vec3d = blockraytraceresult.getPos()
                .subtract(this.getX(), this.getY(), this.getZ());
            this.setVelocity(vec3d);

            this.inGround = true;

            this.onImpact(blockraytraceresult);

            blockstate.onProjectileHit(this.world, blockstate, blockraytraceresult, this);

        }
    }

    protected void onImpact(HitResult result) {

        LivingEntity entityHit = getEntityHit(result, 0.3D);

        if (entityHit != null) {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 1F, 0.9F);
            }

            LivingEntity caster = getCaster();

            if (caster != null) {
                this.getSpellData().attached.tryActivate(Activation.ON_HIT, SpellCtx.onHit(caster, this, entityHit, getSpellData()));
            }
        } else {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.BLOCK_STONE_HIT, 0.7F, 0.9F);
            }
        }

        this.scheduleRemoval();
    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    static Gson GSON = new Gson();

    @Override
    public void writeCustomDataToTag(CompoundTag nbt) {

        try {

            // super.writeCustomDataToTag(nbt);

            nbt.putInt("xTile", this.xTile);
            nbt.putInt("yTile", this.yTile);
            nbt.putInt("zTile", this.zTile);

            nbt.putByte("inGround", (byte) (this.inGround ? 1 : 0));

            nbt.putInt("deathTime", this.getDeathTime());

            nbt.putString("data", GSON.toJson(spellData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbt) {

        try {

//            super.readCustomDataFromTag(nbt);

            this.xTile = nbt.getInt("xTile");
            this.yTile = nbt.getInt("yTile");
            this.zTile = nbt.getInt("zTile");

            this.inGround = nbt.getByte("inGround") == 1;

            this.setDeathTime(nbt.getInt("deathTime"));

            this.spellData = GSON.fromJson(nbt.getString("data"), EntitySavedSpellData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    LivingEntity caster;

    public LivingEntity getCaster() {
        if (caster == null) {
            try {
                this.caster = Utilities.getLivingEntityByUUID(world, UUID.fromString(getSpellData().caster_uuid));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return caster;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPELL_DATA, new CompoundTag());
        super.initDataTracker();
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean canFly() {
        return false;
    }

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

    @Override
    public ItemStack getItem() {

        try {
            Item item = Registry.ITEM.get(new Identifier(getSpellData().item_id));
            if (item != null) {
                return new ItemStack(item);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return new ItemStack(Items.AIR);
    }

    @Override
    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.setNoGravity(!holder.getOrDefault(MapField.GRAVITY, true));
        this.deathTime = holder.get(MapField.LIFESPAN_TICKS)
            .intValue();

        data.item_id = holder.get(MapField.ITEM);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("spell", GSON.toJson(spellData));
        dataTracker.set(SPELL_DATA, nbt);
        this.setOwner(caster);
    }
}