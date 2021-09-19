package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.phys.EntityHitResult;

public class StopCastingIfInteract implements AttackEntityCallback {

    private static void stop(PlayerEntity player) {
        if (player.level.isClientSide) {
            return;
        }
        EntitySpellCap.ISpellsCap data = Load.spells(player);

        if (data.getCastingData()
            .isCasting()) {
            data.getCastingData()
                .cancelCast(player);
            data.syncToClient(player);
        }
    }

    @Override
    public ActionResultType interact(PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
        stop(playerEntity);
        return ActionResultType.PASS;
    }
}

