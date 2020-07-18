package com.robertx22.mine_and_slash.event_hooks.ontick;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.List;

public class OnClientTick {

    public static HashMap<String, Integer> COOLDOWN_READY_MAP = new HashMap<>();

    static int TICKS_TO_SHOW = 50;

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {

            PlayerEntity player = MinecraftClient.getInstance().player;

            if (event.player.isPartOf(player)) {

                if (player != null) {

                    PlayerSpellCap.ISpellsCap spells = Load.spells(player);

                    List<String> onCooldown = spells.getCastingData()
                        .getSpellsOnCooldown();

                    spells.getCastingData()
                        .onTimePass(player, spells, 1); // ticks spells so i dont need to sync packets every tick

                    List<String> onCooldownAfter = spells.getCastingData()
                        .getSpellsOnCooldown();

                    onCooldown.removeAll(onCooldownAfter);

                    COOLDOWN_READY_MAP.entrySet()
                        .forEach(x -> x.setValue(x.getValue() - 1));

                    onCooldown.forEach(x -> {
                        COOLDOWN_READY_MAP.put(x, TICKS_TO_SHOW);
                        x.isEmpty();
                    });

                }
            }
        }
    }
}