package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.IMyRenderAsItem;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleProjectileEntity extends PersistentProjectileEntity implements IMyRenderAsItem, IDatapackSpellEntity {

    EntitySavedSpellData spellData;

    private int xTile;
    private int yTile;
    private int zTile;

    protected boolean inGround;

    private int ticksInGround = 0;

    private static final TrackedData<NbtCompound> SPELL_DATA = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<String> ENTITY_NAME = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Boolean> EXPIRE_ON_ENTITY_HIT = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HIT_ALLIES = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PIERCE = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> DEATH_TIME = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> EXPIRE_ON_BLOCK_HIT = DataTracker.registerData(SimpleProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public Entity ignoreEntity;

    boolean collidedAlready = false;

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

    @Override // seems to help making it hit easier?
    public float getTargetingMargin() {
        return 1.0F;
    }

    public int getTicksInGround() {
        return this.ticksInGround;
    }

    public int getDeathTime() {
        return dataTracker.get(DEATH_TIME);
    }

    public void setDeathTime(int newVal) {
        this.dataTracker.set(DEATH_TIME, newVal);
    }

    public SimpleProjectileEntity(EntityType<? extends Entity> type, World worldIn) {
        super((EntityType<? extends PersistentProjectileEntity>) type, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
    }

    public Entity getEntityHit(HitResult result, double radius) {

        EntityHitResult enres = null;

        if (result instanceof EntityHitResult) {
            enres = (EntityHitResult) result;
        }

        if (enres == null) {
            return null;
        }

        if (enres.getEntity() instanceof Entity) {
            if (enres.getEntity() != this.getCaster()) {
                return enres.getEntity();
            }
        }

        if (getCaster() != null) {
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

                return closest;
            }
        }

        return null;

    }

    public void onTick() {

        this.getSpellData()
            .getSpell()
            .getAttached()
            .tryActivate(getEntityName(), SpellCtx.onTick(getCaster(), this, getSpellData()));

    }

    @Override
    public void remove() {

        LivingEntity caster = getCaster();

        if (caster != null) {
            this.getSpellData()
                .getSpell()
                .getAttached()
                .tryActivate(getEntityName(), SpellCtx.onExpire(caster, this, getSpellData()));
        }

        super.remove();
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    public final void tick() {

        if (this.removeNextTick) {
            this.remove();
            return;
        }

        try {
            super.tick();
        } catch (Exception e) {
            e.printStackTrace();
            this.scheduleRemoval();
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
            this.scheduleRemoval();
        }

    }

    @Override
    protected EntityHitResult getEntityCollision(Vec3d pos, Vec3d posPlusMotion) {

        EntityHitResult res = ProjectileUtil.getEntityCollision(
            this.world, this, pos, posPlusMotion, this.getBoundingBox()
                .stretch(this.getVelocity())
                .expand(1D), (e) -> {
                return !e.isSpectator() && e.collides() && e instanceof Entity && e != this.getCaster() && e != this.ignoreEntity;
            });

        if (!this.dataTracker.get(HIT_ALLIES)) {
            if (res != null && getCaster() != null && res.getEntity() instanceof LivingEntity) {
                if (AllyOrEnemy.allies.is(getCaster(), (LivingEntity) res.getEntity())) {
                    return null; // don't hit allies with spells, let them pass
                }
            }
        }
        return res;
    }

    @Override
    protected void onCollision(HitResult raytraceResultIn) {

        HitResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == HitResult.Type.ENTITY) {
            this.onImpact(raytraceResultIn);
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        } else if (raytraceresult$type == HitResult.Type.BLOCK) {

            if (collidedAlready) {
                return;
            }
            collidedAlready = true;

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

        Entity entityHit = getEntityHit(result, 0.3D);

        if (entityHit != null) {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 1F, 0.9F);
            }

            LivingEntity caster = getCaster();

            LivingEntity en = null;

            if (entityHit instanceof LivingEntity == false) {
                // HARDCODED support for dumb ender dragon non living entity dragon parts
                if (entityHit instanceof EnderDragonPart) {
                    EnderDragonPart part = (EnderDragonPart) entityHit;
                    if (!part.isInvulnerableTo(DamageSource.mob(caster))) {
                        en = part.owner;
                    }
                }
            } else if (entityHit instanceof LivingEntity) {
                en = (LivingEntity) entityHit;
            }

            if (en == null) {
                return;
            }

            if (caster != null) {
                if (!Load.spells(caster)
                    .alreadyHit(this, en)) {
                    this.getSpellData()
                        .getSpell()
                        .getAttached()
                        .tryActivate(getEntityName(), SpellCtx.onHit(caster, this, en, getSpellData()));
                }
            }

        } else {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.BLOCK_STONE_HIT, 0.7F, 0.9F);
            }
        }

        if (entityHit != null) {
            if (!dataTracker.get(EXPIRE_ON_ENTITY_HIT)) {
                return;
            } else {
                scheduleRemoval();
            }
        }

        if (result instanceof BlockHitResult && dataTracker.get(EXPIRE_ON_BLOCK_HIT)) {
            scheduleRemoval();
        }

    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    static Gson GSON = new Gson();

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

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
    public void readCustomDataFromNbt(NbtCompound nbt) {

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
        this.dataTracker.startTracking(SPELL_DATA, new NbtCompound());
        this.dataTracker.startTracking(ENTITY_NAME, "");
        this.dataTracker.startTracking(EXPIRE_ON_ENTITY_HIT, true);
        this.dataTracker.startTracking(EXPIRE_ON_BLOCK_HIT, true);
        this.dataTracker.startTracking(HIT_ALLIES, false);
        this.dataTracker.startTracking(PIERCE, false);
        this.dataTracker.startTracking(DEATH_TIME, 100);
        super.initDataTracker();
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    public EntitySavedSpellData getSpellData() {
        try {
            if (world.isClient) {
                if (spellData == null) {
                    NbtCompound nbt = dataTracker.get(SPELL_DATA);
                    if (nbt != null) {
                        this.spellData = GSON.fromJson(nbt.getString("spell"), EntitySavedSpellData.class);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
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

    public String getEntityName() {
        return dataTracker.get(ENTITY_NAME);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        // don't allow player to pickup lol
    }

    @Override
    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.pickupType = PickupPermission.DISALLOWED;

        this.setNoGravity(!holder.getOrDefault(MapField.GRAVITY, true));
        this.setDeathTime(holder.get(MapField.LIFESPAN_TICKS)
            .intValue());

        this.dataTracker.set(EXPIRE_ON_ENTITY_HIT, holder.getOrDefault(MapField.EXPIRE_ON_ENTITY_HIT, true));
        this.dataTracker.set(EXPIRE_ON_BLOCK_HIT, holder.getOrDefault(MapField.EXPIRE_ON_BLOCK_HIT, true));
        this.dataTracker.set(HIT_ALLIES, holder.getOrDefault(MapField.HITS_ALLIES, false));

        this.checkBlockCollision();

        if (data.pierce) {
            this.dataTracker.set(EXPIRE_ON_ENTITY_HIT, false);
        }

        data.item_id = holder.get(MapField.ITEM);
        NbtCompound nbt = new NbtCompound();
        nbt.putString("spell", GSON.toJson(spellData));
        dataTracker.set(SPELL_DATA, nbt);
        this.setOwner(caster);

        String name = holder.get(MapField.ENTITY_NAME);
        dataTracker.set(ENTITY_NAME, name);

    }
}