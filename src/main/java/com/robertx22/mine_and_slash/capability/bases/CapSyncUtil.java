package com.robertx22.mine_and_slash.capability.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public class CapSyncUtil {

    public static void syncAll(PlayerEntity player) {
        syncEntityCap(player);
        syncSpells(player);
        syncStatPoints(player);
    }

    public static void syncSpells(PlayerEntity player) {
        PlayerSpellCap.ISpellsCap data = Load.spells(player);
        data.syncToClient(player);
    }

    public static void syncEntityCap(PlayerEntity player) {
        EntityCap.UnitData data = Load.Unit(player);
        data.syncToClient(player);
    }

    public static void syncStatPoints(PlayerEntity player) {
        Load.statPoints(player)
            .syncToClient(player);
    }

}
