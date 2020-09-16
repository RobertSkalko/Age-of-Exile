package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.common.ConfigRegister;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.vanilla_mc.packets.OnLoginClientPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.concurrent.TimeUnit;

public class OnLogin {

    public static void onLoad(ServerPlayerEntity player) {

        try {

            Watch watch = new Watch();
            watch.unit = TimeUnit.SECONDS;
            watch.min = 1;
            Watch mili = null;
            if (MMORPG.RUN_DEV_TOOLS) {
                mili = new Watch();
            }
            Packets.sendToClient(player, new OnLoginClientPacket(OnLoginClientPacket.When.BEFORE));
            ConfigRegister.CONFIGS.values()
                .forEach(x -> x.sendToClient(player));
            Rarities.sendAllPacketsToClientOnLogin(player);
            SlashRegistry.sendAllPacketsToClientOnLogin(player);
            Packets.sendToClient(player, new OnLoginClientPacket(OnLoginClientPacket.When.AFTER));
            SlashRegistry.restoreFromBackupifEmpty();

            watch.print("Sending Login Packets ");
            if (MMORPG.RUN_DEV_TOOLS) {
                mili.print("Sending Login Packets ");
            }

            Load.spells(player)
                .getCastingData()
                .clearLockedSpells(player);

            if (MMORPG.RUN_DEV_TOOLS) {
                player.sendMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            if (Load.hasUnit(player)) {

                UnitData data = Load.Unit(player);

                SlashRegistry.restoreFromBackupifEmpty();
                data.onLogin(player);

                data.syncToClient(player);

            } else {
                player.sendMessage(
                    new LiteralText("Error, player has no capability!" + Ref.MOD_NAME + " mod is broken!"), false);
            }

        } catch (
            Exception e) {
            e.printStackTrace();
        }

        SlashRegistry.restoreFromBackupifEmpty();
    }

    public static void GiveStarterItems(PlayerEntity player) {

        if (player.world.isClient) {
            return;
        }

        player.inventory.insertStack(new ItemStack(ModRegistry.MISC_ITEMS.NEWBIE_GEAR_BAG));

    }

}
