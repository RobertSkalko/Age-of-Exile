package com.robertx22.age_of_exile.capability.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public class CapSyncUtil {

    public static void syncAll(PlayerEntity player) {
        syncEntityCap(player);
        syncSpells(player);
        syncRpgStats(player);
    }

    public static void syncSpells(PlayerEntity player) {
        EntitySpellCap.ISpellsCap data = Load.spells(player);
        data.syncToClient(player);
    }

    public static void syncEntityCap(PlayerEntity player) {
        EntityCap.UnitData data = Load.Unit(player);
        data.syncToClient(player);
    }

    public static void syncRpgStats(PlayerEntity player) {
        Load.playerRPGData(player)
            .syncToClient(player);
    }

}
