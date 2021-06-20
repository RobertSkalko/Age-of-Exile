package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;

import java.util.HashMap;

@Storable
public class PlayerLoadoutsData {

    public static String SWITCH_COOLDOWN_ID = "loadout_cd";

    @Store
    public HashMap<Integer, OneLoadoutData> loadouts = new HashMap<>();

    public void load(Integer num, PlayerEntity p) {

        int cdticks = Load.Unit(p)
            .getCooldowns()
            .getCooldownTicks(SWITCH_COOLDOWN_ID);

        if (WorldUtils.isDungeonWorld(p.world)) {
            p.sendMessage(new LiteralText("You can't switch loadouts inside dungeons."), false);
            return;
        }
        if (Load.Unit(p)
            .getCooldowns()
            .isOnCooldown(SWITCH_COOLDOWN_ID)) {
            p.sendMessage(new LiteralText("You can't switch loadouts that often! Cooldown remaining: " + cdticks / 20 + "s"), false);
            return;
        }

        OneLoadoutData data = new OneLoadoutData();
        data.save(p);

        if (loadouts.containsKey(num)) {
            // load saved character and replace it with current one
            loadouts.get(num)
                .load(p);
            loadouts.put(num, data);
        } else {

            //new character

            for (PlayerCaps cap : PlayerCaps.values()) {
                if (cap.shouldSaveToLoadout()) {
                    cap.getCap(p)
                        .fromTag(new NbtCompound());
                }
            }

            loadouts.put(num, data);

        }

        Load.Unit(p)
            .getCooldowns()
            .setOnCooldown(SWITCH_COOLDOWN_ID, ModConfig.get().Server.SWITCHING_LOADOUT_COOLDOWN_TICKS);

    }

}
