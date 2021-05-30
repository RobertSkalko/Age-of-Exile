package com.robertx22.age_of_exile.event_hooks.ontick;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class EnsureTeleportData {

    ServerPlayerEntity player;
    Consumer<EnsureTeleportData> action;
    int ticksLeft;
    int ticks = 0;
    BlockPos whereShouldTeleport;

    int origTicksLeft;

    public EnsureTeleportData(ServerPlayerEntity player, Consumer<EnsureTeleportData> action, int ticksLeft, BlockPos whereShouldTeleport) {
        this.player = player;
        this.action = action;
        this.ticksLeft = ticksLeft;
        this.whereShouldTeleport = whereShouldTeleport;

        this.origTicksLeft = ticksLeft;
    }

    public void resetTicks() {
        this.ticks = 0;
        this.ticksLeft = origTicksLeft;
    }
}
