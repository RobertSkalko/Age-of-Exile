package com.robertx22.age_of_exile.capability.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public class CapSyncUtil {

    public static void syncAll(PlayerEntity player) {
        syncEntityCap(player);
        syncSpells(player);
        syncFavor(player);
        syncStats(player);
    }

    public static void syncSpells(PlayerEntity player) {
        EntitySpellCap.ISpellsCap data = Load.spells(player);
        data.syncToClient(player);
    }

    public static void syncEntityCap(PlayerEntity player) {
        EntityCap.UnitData data = Load.Unit(player);
        data.syncToClient(player);
    }

    public static void syncFavor(PlayerEntity player) {
        Load.favor(player)
            .syncToClient(player);
    }

    public static void syncStats(PlayerEntity player) {
        Load.statPoints(player)
            .syncToClient(player);
    }

}
