package com.robertx22.mine_and_slash.database.data.spells.entities.bases;

import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.uncommon.datasaving.EntitySpellDataSaving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jline.utils.Log;



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
        if (this.spellData == null || this.spellData.getCaster(world) == null) {
            Log.info(
                "Removing spell entity because data or caster is null. This happens sometimes and is normal, i'm " +
                    "just logging to see how often it happens.");
            this.remove();
        } else {
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

    @Nullable
    public LivingEntity getCaster() {
        return getSpellData().getCaster(world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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

    @Override
    public void writeSpawnData(PacketByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        writeCustomDataToTag(nbt);
        buf.writeCompoundTag(nbt);
    }

    @Override
    public void readSpawnData(PacketByteBuf buf) {
        CompoundTag nbt = buf.readCompoundTag();
        this.readCustomDataFromTag(nbt);
    }

}