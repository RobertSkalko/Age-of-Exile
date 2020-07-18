package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OnLoginClientPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnLogin {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getPlayer().world.isClient) {
            return;
        }

        try {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

            MMORPG.sendToClient(new OnLoginClientPacket(OnLoginClientPacket.When.BEFORE), player);
            ConfigRegister.CONFIGS.values()
                .forEach(x -> x.sendToClient(player));
            Rarities.sendAllPacketsToClientOnLogin(player);
            SlashRegistry.sendAllPacketsToClientOnLogin(player);

            MMORPG.sendToClient(new OnLoginClientPacket(OnLoginClientPacket.When.AFTER), player);

            SlashRegistry.restoreFromBackupifEmpty();

            if (MMORPG.RUN_DEV_TOOLS) {
                player.sendMessage(Chats.Dev_tools_enabled_contact_the_author.locName()
                    .setStyle(new Style().setColor(Styles.RED)));
            }

            if (Load.hasUnit(player)) {

                UnitData data = Load.Unit(player);

                SlashRegistry.restoreFromBackupifEmpty();
                data.onLogin(player);

                data.syncToClient(player);

            } else {
                player.sendMessage(
                    new LiteralText("Error, player has no capability!" + Ref.MOD_NAME + " mod is broken!"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        SlashRegistry.restoreFromBackupifEmpty();

    }

    public static void GiveStarterItems(PlayerEntity player) {

        if (player.world.isClient) {
            return;
        }

        player.inventory.insertStack(new ItemStack(ModRegistry.MISC_ITEMS.NEWBIE_GEAR_BAG.get()));

    }

}
