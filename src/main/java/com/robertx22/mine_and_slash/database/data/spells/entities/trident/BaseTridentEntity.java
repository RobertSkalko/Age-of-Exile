package com.robertx22.mine_and_slash.database.data.spells.entities.trident;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.uncommon.datasaving.EntitySpellDataSaving;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class BaseTridentEntity extends TridentEntity implements ISpellEntity {

    EntitySpellData spellData;

    public BaseTridentEntity(EntityType<? extends TridentEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbt) {
        this.spellData = EntitySpellDataSaving.Load(nbt);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbt) {
        EntitySpellDataSaving.Save(nbt, spellData);
    }

    public LivingEntity getCaster() {
        return getSpellData().getCaster(world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
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
    public void tick() {

        if (this.inGroundTime > 50) {
            this.removed = true;
        }

        if (world.isClient) {
            if (this.age > 2) {
                for (int i = 0; i < 5; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getX(), getY(), getZ(), 0.1F);
                    ParticleUtils.spawn(ModRegistry.PARTICLES.THUNDER, world, p);
                }
            }
        }

        super.tick();

        if (removed) {
            remove();
        }
    }

    public void onHit(LivingEntity en) {

        Entity shooter = this.getCaster();
        DamageEffect dmg = dealSpellDamageTo((LivingEntity) en, new Options().activatesEffect(false));
        dmg.Activate();
    }

    @Override
    protected void onEntityHit(EntityHitResult ray) {

        if (!world.isClient) {

            Entity entity = ray.getEntity();
            if (entity instanceof LivingEntity) {
                Entity shooter = this.getCaster();

                if (entity == shooter) {
                    return;
                }
                this.setVelocity(this.getVelocity()
                    .multiply(-0.01D, -0.1D, -0.01D)); // bounce back

                this.onHit((LivingEntity) entity);

                this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 8F, 1.0F);

            }
        }
    }

    // DATA TRACKER STUFF
    private static final TrackedData<CompoundTag> SPELL_DATA = DataTracker.registerData(BaseTridentEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPELL_DATA, new CompoundTag());
        super.initDataTracker();
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
