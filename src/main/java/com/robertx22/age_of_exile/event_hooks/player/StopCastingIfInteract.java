package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class StopCastingIfInteract implements AttackEntityCallback {

    private static void stop(PlayerEntity player) {
        if (player.world.isClient) {
            return;
        }
        PlayerSpellCap.ISpellsCap data = Load.spells(player);

        if (data.getCastingData()
            .isCasting()) {
            data.getCastingData()
                .cancelCast(player);
            data.syncToClient(player);
        }
    }

    @Override
    public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
        stop(playerEntity);
        return ActionResult.PASS;
    }
}

