package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.List;

public class OnClientTick implements ClientTickEvents.EndTick {

    public static HashMap<String, Integer> COOLDOWN_READY_MAP = new HashMap<>();

    static int TICKS_TO_SHOW = 50;

    private static int NO_MANA_SOUND_COOLDOWN = 0;

    public static boolean canSoundNoMana() {
        return NO_MANA_SOUND_COOLDOWN <= 0;
    }

    public static void setNoManaSoundCooldown() {
        NO_MANA_SOUND_COOLDOWN = 30;
    }

    @Override
    public void onEndTick(MinecraftClient mc) {

        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) {
            return;
        }

        if (player.isPartOf(player)) {

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

            if (KeybindsRegister.noSpellKeysAreHeld()) {
                if (spells.getCastingData()
                    .isCasting()) {
                    Spell spell = spells.getCastingData()
                        .getSpellBeingCast();
                    if (spell.config.cast_type == SpellCastType.USE_ITEM) {
                        if (Gear.has(player.getMainHandStack())) {
                            ClientOnly.stopUseKey();
                            Packets.sendToServer(new TellServerToCancelSpellCast());
                        }
                    }
                }
            }

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