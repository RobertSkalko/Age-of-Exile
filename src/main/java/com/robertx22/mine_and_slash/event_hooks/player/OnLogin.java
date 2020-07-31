package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OnLoginClientPacket;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

public class OnLogin implements ServerEntityEvents.Load {

    @Override
    public void onLoad(Entity entity, ServerWorld serverWorld) {

        if (!(entity instanceof ServerPlayerEntity)) {
            return;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) entity;

        try {

            Packets.sendToClient(player, new OnLoginClientPacket(OnLoginClientPacket.When.BEFORE));
            ConfigRegister.CONFIGS.values()
                .forEach(x -> x.sendToClient(player));
            Rarities.sendAllPacketsToClientOnLogin(player);
            SlashRegistry.sendAllPacketsToClientOnLogin(player);

            Packets.sendToClient(player, new OnLoginClientPacket(OnLoginClientPacket.When.AFTER));

            SlashRegistry.restoreFromBackupifEmpty();

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

            ContainerProviderRegistry.INSTANCE.openContainer(ModRegistry.CONTAINERS.HOTBAR_SETUP, player, buf -> buf.writeInt(5));

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
