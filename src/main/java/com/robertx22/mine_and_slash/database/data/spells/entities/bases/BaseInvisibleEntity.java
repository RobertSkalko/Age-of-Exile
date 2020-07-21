package com.robertx22.mine_and_slash.database.data.spells.entities.bases;

import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.uncommon.datasaving.EntitySpellDataSaving;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class BaseInvisibleEntity extends Entity implements IMyRenderAsItem, ISpellEntity {

    EntitySpellData spellData;

    public BaseInvisibleEntity(EntityType type, World world) {
        super(type, world);

        this.setNoGravity(true);
        this.setVelocity(new Vec3d(0, 0, 0));
        this.setInvulnerable(true);
    }

    public abstract void onTick();

    @Override
    public void tick() {

        if (!world.isClient) {
            if (this.spellData == null || this.spellData.getCaster(world) == null) {
                this.remove();
                return;
            }
        }
        try {
            super.tick();
            onTick();

            if (this.age > getLifeInTicks() && getLifeInTicks() != -1) {
                this.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag nbt) {
        this.spellData = EntitySpellDataSaving.Load(nbt);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag nbt) {
        EntitySpellDataSaving.Save(nbt, spellData);
    }

    public LivingEntity getCaster() {
        return getSpellData().getCaster(world);
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
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    // DATA TRACKER STUFF
    private static final TrackedData<CompoundTag> SPELL_DATA = DataTracker.registerData(BaseInvisibleEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPELL_DATA, new CompoundTag());
    }

    @Override
    public EntitySpellData getSpellData() {
        if (world.isClient) {
            if (spellData == null) {
                CompoundTag nbt = dataTracker.get(SPELL_DATA);
                if (nbt != null) {
                    this.spellData = LoadSave.Load(EntitySpellData.class, new EntitySpellData(), nbt, "spell");
                }
            }
        }
        return spellData;
    }

    @Override
    public void setSpellData(EntitySpellData data) {
        this.spellData = data;
        CompoundTag nbt = new CompoundTag();
        LoadSave.Save(spellData, nbt, "spell");
        dataTracker.set(SPELL_DATA, nbt);
    }
    // DATA TRACKER STUFF

}