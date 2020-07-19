package com.robertx22.mine_and_slash.database.data.spells.entities.bases;

import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.uncommon.datasaving.EntitySpellDataSaving;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.sun.istack.internal.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityBaseProjectile extends ProjectileEntity implements ThrownEntity, IMyRenderAsItem,
    ISpellEntity {

    EntitySpellData spellData;

    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    protected boolean inGround;
    public int throwableShake;

    private int ticksInGround;
    private int ticksInAir;
    private int deathTime = 80;
    private int airProcTime;
    private boolean doGroundProc;

    public Entity ignoreEntity;

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    public abstract double radius();

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

    public boolean getDoExpireProc() {
        return this.doGroundProc;
    }

    public int getTicksInAir() {
        return this.ticksInAir;
    }

    public int getTicksInGround() {
        return this.ticksInGround;
    }

    public void setTicksInAir(int newVal) {
        this.ticksInAir = newVal;
    }

    public void setTicksInGround(int newVal) {
        this.ticksInGround = newVal;
    }

    public int getAirProcTime() {
        return this.airProcTime;
    }

    public void setAirProcTime(int newVal) {
        this.airProcTime = newVal;
    }

    public int getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(int newVal) {
        this.deathTime = newVal;
    }

    public void setDoExpireProc(boolean newVal) {
        this.doGroundProc = newVal;
    }

    public EntityBaseProjectile(EntityType<? extends Entity> type, World worldIn) {
        super((EntityType<? extends ProjectileEntity>) type, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;

        this.setDeathTime(this.getDefaultLifeInTicks());

    }

    @Nullable
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

        List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, getPosVector())
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

    public abstract void onTick();

    @Override
    public final void tick() {

        if (this.spellData == null || this.spellData.getCaster(world) == null) {
            this.remove();
        } else {
            try {

                super.tick();
                onTick();

                if (this.inGround) {
                    ticksInGround++;
                } else {
                    ticksInAir++;
                }

                if (this.age >= this.getDeathTime()) {
                    onExpireProc(this.getCaster());
                    this.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d pos, Vec3d posPlusMotion) {

        return ProjectileUtil.getEntityCollision(
            this.world, this, pos, posPlusMotion, this.getBoundingBox()
                .stretch(this.getVelocity())
                .expand(1D), (e) -> {
                return !e.isSpectator() && e.collides() && e instanceof LivingEntity && e != this.getCaster() && e != this.ignoreEntity;
            });

    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

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
            Vec3d vec3d1 = vec3d.normalize()
                .multiply((double) 0.05F);

            this.inGround = true;

            this.setPos(vec3d1.x, vec3d1.y, vec3d1.z);

            this.onImpact(blockraytraceresult);

            blockstate.onProjectileHit(this.world, blockstate, blockraytraceresult, this);

        }
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected abstract void onImpact(HitResult result);

    /**
     * (abstract) Protected helper method to write subclass entity dataInstance to NBT.
     */

    @Override
    public void writeCustomDataToTag(CompoundTag nbt) {

        nbt.putInt("xTile", this.xTile);
        nbt.putInt("yTile", this.yTile);
        nbt.putInt("zTile", this.zTile);

        nbt.putByte("shake", (byte) this.throwableShake);
        nbt.putByte("inGround", (byte) (this.inGround ? 1 : 0));

        nbt.putBoolean("doGroundProc", this.getDoExpireProc());
        nbt.putInt("airProcTime", this.getAirProcTime());
        nbt.putInt("deathTime", this.getDeathTime());

        EntitySpellDataSaving.Save(nbt, spellData);
    }

    /**
     * (abstract) Protected helper method to read subclass entity dataInstance from NBT.
     */
    @Override
    public void readCustomDataFromTag(CompoundTag nbt) {

        this.xTile = nbt.getInt("xTile");
        this.yTile = nbt.getInt("yTile");
        this.zTile = nbt.getInt("zTile");

        this.throwableShake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;

        this.setDoExpireProc(nbt.getBoolean("doGroundProc"));
        this.setAirProcTime(nbt.getInt("airProcTime"));
        this.setDeathTime(nbt.getInt("deathTime"));

        this.spellData = EntitySpellDataSaving.Load(nbt);
    }

    protected void setPos(LivingEntity caster) {
        Vec3d look = caster.getRotationVector();
        updatePosition(caster.getX() - look.x, caster.getY() - look.y + 1.3, caster.getZ() - look.z);
    }

    ////////////////////////////////////////////////////////

    @Nullable
    public LivingEntity getCaster() {
        return this.getSpellData()
            .getCaster(world);
    }

    @Override
    protected void initDataTracker() {
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

    @Override
    public EntitySpellData getSpellData() {
        return spellData;
    }

    @Override
    public void setSpellData(EntitySpellData data) {
        this.spellData = data;
    }

}