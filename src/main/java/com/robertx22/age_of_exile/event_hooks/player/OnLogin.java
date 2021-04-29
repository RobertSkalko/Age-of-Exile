package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SyncTime;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.vanilla_mc.packets.ForceChoosingRace;
import com.robertx22.age_of_exile.vanilla_mc.packets.OnLoginClientPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class OnLogin {

    public static void onLoad(ServerPlayerEntity player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {
            total = new Watch();
        }

        try {

            Packets.sendToClient(player, new OnLoginClientPacket(SyncTime.ON_LOGIN, OnLoginClientPacket.When.BEFORE));
            Database.sendPacketsToClient(player, SyncTime.ON_LOGIN);
            Packets.sendToClient(player, new OnLoginClientPacket(SyncTime.ON_LOGIN, OnLoginClientPacket.When.AFTER));
            Database.restoreFromBackupifEmpty();

            Load.perks(player)
                .syncToClient(player);

            if (MMORPG.RUN_DEV_TOOLS) {
                player.sendMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            if (Load.hasUnit(player)) {

                UnitData data = Load.Unit(player);

                data.onLogin(player);

                data.syncToClient(player);

                if (!data.hasRace()) {
                    Packets.sendToClient(player, new ForceChoosingRace());
                }

            } else {
                player.sendMessage(
                    new LiteralText("Error, player has no capability!" + Ref.MOD_NAME + " mod is broken!"), false);
            }

        } catch (
            Exception e) {
            e.printStackTrace();
        }

        Database.restoreFromBackupifEmpty();

        if (MMORPG.RUN_DEV_TOOLS) {
            total.print("Total on login actions took ");
        }
    }

    public static void GiveStarterItems(PlayerEntity player) {

        if (player.world.isClient) {
            return;
        }

        player.inventory.insertStack(new ItemStack(ModRegistry.MISC_ITEMS.NEWBIE_GEAR_BAG));

    }

}
