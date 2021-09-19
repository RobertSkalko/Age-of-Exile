package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.entities.IDatapackSpellEntity;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.server.ServerWorld;

public class SpellUtils {

    public static void summonLightningStrike(Entity entity) {

        LightningBolt lightningboltentity = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);  //boolean true means it's only an effect!'

        lightningboltentity.setPosRaw((double) entity.getX() + 0.5D,
            (double) entity.getY(),
            (double) entity.getZ() + 0.5D);

        lightningboltentity.setVisualOnly(true);

        SoundUtils.playSound(entity, SoundEvents.LIGHTNING_BOLT_IMPACT, 1, 1);

        addLightningBolt(((ServerWorld) entity.level), lightningboltentity);

    }

    public static void addLightningBolt(ServerWorld world, LightningBolt entityIn) {
        world.getServer()
            .getPlayerList()
            .broadcast((PlayerEntity) null, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 50, world.dimension()
                , new ClientboundAddEntityPacket(entityIn));
    }

    public static void shootProjectile(Vector3d pos, AbstractArrow projectile, LivingEntity caster, float speed,
                                       float pitch, float yaw) {

        // pos = pos.add(caster.getRotationVector()
        //   .multiply(0.25F));

        ((Entity) projectile).setPos(pos.x, caster.getEyeY() - 0.1F, pos.z);

        projectile.shootFromRotation(caster, pitch, yaw, 0, speed, 1F);

    }

    public static void initSpellEntity(Entity spellEntity, LivingEntity caster, EntitySavedSpellData data, MapHolder holder) {

        IDatapackSpellEntity se = (IDatapackSpellEntity) spellEntity;
        se.init(caster, data, holder);
    }

}
