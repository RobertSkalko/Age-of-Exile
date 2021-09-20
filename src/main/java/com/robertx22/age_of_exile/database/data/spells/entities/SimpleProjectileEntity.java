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
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleProjectileEntity extends AbstractArrowEntity implements IMyRenderAsItem, IDatapackSpellEntity {

    EntitySavedSpellData spellData;

    private int xTile;
    private int yTile;
    private int zTile;

    protected boolean inGround;

    private int ticksInGround = 0;

    private static final DataParameter<CompoundNBT> SPELL_DATA = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<String> ENTITY_NAME = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> EXPIRE_ON_ENTITY_HIT = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HIT_ALLIES = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PIERCE = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DEATH_TIME = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> EXPIRE_ON_BLOCK_HIT = EntityDataManager.defineId(SimpleProjectileEntity.class, DataSerializers.BOOLEAN);

    public Entity ignoreEntity;

    boolean collidedAlready = false;

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected boolean onExpireProc(LivingEntity caster) {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override // seems to help making it hit easier?
    public float getPickRadius() {
        return 1.0F;
    }

    public int getTicksInGround() {
        return this.ticksInGround;
    }

    public int getDeathTime() {
        return entityData.get(DEATH_TIME);
    }

    public void setDeathTime(int newVal) {
        this.entityData.set(DEATH_TIME, newVal);
    }

    public SimpleProjectileEntity(EntityType<? extends Entity> type, World worldIn) {
        super((EntityType<? extends AbstractArrowEntity>) type, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
    }

    public Entity getEntityHit(RayTraceResult result, double radius) {

        EntityRayTraceResult enres = null;

        if (result instanceof EntityRayTraceResult) {
            enres = (EntityRayTraceResult) result;
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
            List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, position())
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

        if (getCaster() != null) {
            this.getSpellData()
                .getSpell()
                .getAttached()
                .tryActivate(getScoreboardName(), SpellCtx.onTick(getCaster(), this, getSpellData()));

        }
    }

    @Override
    public void remove() {

        LivingEntity caster = getCaster();

        if (caster != null) {
            this.getSpellData()
                .getSpell()
                .getAttached()
                .tryActivate(getScoreboardName(), SpellCtx.onExpire(caster, this, getSpellData()));
        }

        super.remove();
    }

    @Override
    protected float getWaterInertia() {
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
            if (tickCount > 100) {
                this.scheduleRemoval();
            }
            return;
        }

        try {
            onTick();

            if (this.inGround) {
                ticksInGround++;
            }

            if (this.tickCount >= this.getDeathTime()) {
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
    protected EntityRayTraceResult findHitEntity(Vector3d pos, Vector3d posPlusMotion) {

        EntityRayTraceResult res = ProjectileHelper.getEntityHitResult(
            this.level, this, pos, posPlusMotion, this.getBoundingBox()
                .expandTowards(this.getDeltaMovement())
                .inflate(1D), (e) -> {
                return !e.isSpectator() && e.isPickable() && e instanceof Entity && e != this.getCaster() && e != this.ignoreEntity;
            });

        if (!this.entityData.get(HIT_ALLIES)) {
            if (res != null && getCaster() != null && res.getEntity() instanceof LivingEntity) {
                if (AllyOrEnemy.allies.is(getCaster(), (LivingEntity) res.getEntity())) {
                    return null; // don't hit allies with spells, let them pass
                }
            }
        }
        return res;
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onImpact(raytraceResultIn);
            this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {

            if (collidedAlready) {
                return;
            }
            collidedAlready = true;

            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
            BlockState blockstate = this.level.getBlockState(blockraytraceresult.getBlockPos());

            Vector3d vec3d = blockraytraceresult.getLocation()
                .subtract(this.getX(), this.getY(), this.getZ());
            this.setDeltaMovement(vec3d);

            this.inGround = true;

            this.onImpact(blockraytraceresult);

            blockstate.onProjectileHit(this.level, blockstate, blockraytraceresult, this);
        }

    }

    protected void onImpact(RayTraceResult result) {

        Entity entityHit = getEntityHit(result, 0.3D);

        if (entityHit != null) {
            if (level.isClientSide) {
                SoundUtils.playSound(this, SoundEvents.GENERIC_HURT, 1F, 0.9F);
            }

            LivingEntity caster = getCaster();

            LivingEntity en = null;

            if (entityHit instanceof LivingEntity == false) {
                // HARDCODED support for dumb ender dragon non living entity dragon parts
                if (entityHit instanceof EnderDragonPartEntity) {
                    EnderDragonPartEntity part = (EnderDragonPartEntity) entityHit;
                    if (!part.isInvulnerableTo(DamageSource.mobAttack(caster))) {
                        en = part.parentMob;
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
                        .tryActivate(getScoreboardName(), SpellCtx.onHit(caster, this, en, getSpellData()));
                }
            }

        } else {
            if (level.isClientSide) {
                SoundUtils.playSound(this, SoundEvents.STONE_HIT, 0.7F, 0.9F);
            }
        }

        if (entityHit != null) {
            if (!entityData.get(EXPIRE_ON_ENTITY_HIT)) {
                return;
            } else {
                scheduleRemoval();
            }
        }

        if (result instanceof BlockRayTraceResult && entityData.get(EXPIRE_ON_BLOCK_HIT)) {
            scheduleRemoval();
        }

    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    static Gson GSON = new Gson();

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {

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
    public void readAdditionalSaveData(CompoundNBT nbt) {

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
                this.caster = Utilities.getLivingEntityByUUID(level, UUID.fromString(getSpellData().caster_uuid));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return caster;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPELL_DATA, new CompoundNBT());
        this.entityData.define(ENTITY_NAME, "");
        this.entityData.define(EXPIRE_ON_ENTITY_HIT, true);
        this.entityData.define(EXPIRE_ON_BLOCK_HIT, true);
        this.entityData.define(HIT_ALLIES, false);
        this.entityData.define(PIERCE, false);
        this.entityData.define(DEATH_TIME, 100);
        super.defineSynchedData();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public EntitySavedSpellData getSpellData() {
        try {
            if (level.isClientSide) {
                if (spellData == null) {
                    CompoundNBT nbt = entityData.get(SPELL_DATA);
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
            Item item = Registry.ITEM.get(new ResourceLocation(getSpellData().item_id));
            if (item != null) {
                return new ItemStack(item);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return new ItemStack(Items.AIR);
    }

    public String getScoreboardName() {
        return entityData.get(ENTITY_NAME);
    }

    @Override
    public void playerTouch(PlayerEntity player) {
        // don't allow player to pickup lol
    }

    @Override
    public void init(LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.pickup = PickupStatus.DISALLOWED;

        this.setNoGravity(!holder.getOrDefault(MapField.GRAVITY, true));
        this.setDeathTime(holder.get(MapField.LIFESPAN_TICKS)
            .intValue());

        this.entityData.set(EXPIRE_ON_ENTITY_HIT, holder.getOrDefault(MapField.EXPIRE_ON_ENTITY_HIT, true));
        this.entityData.set(EXPIRE_ON_BLOCK_HIT, holder.getOrDefault(MapField.EXPIRE_ON_BLOCK_HIT, true));
        this.entityData.set(HIT_ALLIES, holder.getOrDefault(MapField.HITS_ALLIES, false));

        this.checkInsideBlocks();

        if (data.pierce) {
            this.entityData.set(EXPIRE_ON_ENTITY_HIT, false);
        }

        data.item_id = holder.get(MapField.ITEM);
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("spell", GSON.toJson(spellData));
        entityData.set(SPELL_DATA, nbt);
        this.setOwner(caster);

        String name = holder.get(MapField.ENTITY_NAME);
        entityData.set(ENTITY_NAME, name);

    }
}