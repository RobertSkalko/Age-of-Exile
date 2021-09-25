package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.List;

public class OnClientTick {

    public static HashMap<String, Integer> COOLDOWN_READY_MAP = new HashMap<>();

    static int TICKS_TO_SHOW = 50;

    private static int NO_MANA_SOUND_COOLDOWN = 0;

    public static boolean canSoundNoMana() {
        return NO_MANA_SOUND_COOLDOWN <= 0;
    }

    public static void setNoManaSoundCooldown() {
        NO_MANA_SOUND_COOLDOWN = 30;
    }

    public static void onEndTick(Minecraft mc) {

        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }
        if (player.tickCount < 10) {
            return;
        }
        if (Load.Unit(player) == null) {
            return;
        }

        if (ChatUtils.isChatOpen()) {
            ClientOnly.ticksSinceChatWasOpened = 0;
        } else {
            ClientOnly.ticksSinceChatWasOpened--;
        }

        if (player.is(player)) {

            Load.Unit(player)
                .getResources()
                .onTickBlock(player);

            NO_MANA_SOUND_COOLDOWN--;

            EntitySpellCap.ISpellsCap spells = Load.spells(player);

            List<String> onCooldown = spells.getCastingData()
                .getSpellsOnCooldown(player);

            Load.Unit(player)
                .getCooldowns()
                .onTicksPass(1);

            spells.getCastingData()
                .onTimePass(player, spells, 1); // ticks spells on client

            List<String> onCooldownAfter = spells.getCastingData()
                .getSpellsOnCooldown(player);

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