package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class OnLogin {

    public static void onLoad(ServerPlayerEntity player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {
            total = new Watch();
        }

        try {

            if (!player.getServer()
                .areCommandBlocksEnabled()) {
                player.sendMessage(new LiteralText("Command blocks are disabled, this will stop you from playing Age of Exile Dungeons!").formatted(Formatting.RED), false);
                player.sendMessage(new LiteralText("To enable go to your server.properties file and put enable-command-block as true.").formatted(Formatting.GREEN), false);
            }

            Load.perks(player)
                .syncToClient(player);

            Load.playerMaps(player)
                .syncToClient(player);

            if (MMORPG.RUN_DEV_TOOLS) {
                player.sendMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            UnitData data = Load.Unit(player);

            data.onLogin(player);

            data.syncToClient(player);

        } catch (
            Exception e) {
            e.printStackTrace();
        }

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
