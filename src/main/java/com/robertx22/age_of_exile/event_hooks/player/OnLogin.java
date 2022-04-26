package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameRules;

import java.util.Collection;
import java.util.stream.Collectors;

public class OnLogin {

    public static void onLoad(ServerPlayerEntity player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {

            total = new Watch();

            if (true) {
                MinecraftServer server = player.getServer();
                GameRules rules = player.getServer()
                    .getGameRules();

                rules.getRule(GameRules.RULE_DOMOBSPAWNING)
                    .set(false, server);
                rules.getRule(GameRules.RULE_WEATHER_CYCLE)
                    .set(false, server);
                rules.getRule(GameRules.RULE_DAYLIGHT)
                    .set(false, server);
                rules.getRule(GameRules.RULE_KEEPINVENTORY)
                    .set(true, server);
            }
        }

        try {

            if (!player.getServer()
                .isCommandBlockEnabled()) {
                player.displayClientMessage(new StringTextComponent("Command blocks are disabled, this will stop you from playing Age of Exile Dungeons!").withStyle(TextFormatting.RED), false);
                player.displayClientMessage(new StringTextComponent("To enable go to your server.properties file and put enable-command-block as true.").withStyle(TextFormatting.GREEN), false);
            }

            CapSyncUtil.syncAll(player);

            if (MMORPG.RUN_DEV_TOOLS) {
                player.displayClientMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            EntityData data = Load.Unit(player);

            data.onLogin(player);

            tryUnlockAllCraftingRecipes(player);

            data.syncToClient(player);

        } catch (
            Exception e) {
            e.printStackTrace();
        }

        if (MMORPG.RUN_DEV_TOOLS) {
            total.print("Total on login actions took ");
        }
    }

    public static void tryUnlockAllCraftingRecipes(ServerPlayerEntity player) {
        // todo this might be very laggy on large modpacks
        Collection<IRecipe<?>> recipes = player.level.getRecipeManager()
            .getAllRecipesFor(IRecipeType.CRAFTING)
            .stream()
            .filter(x -> x.getId()
                .getNamespace()
                .equals(SlashRef.MODID))
            .collect(Collectors.toList());
        player.awardRecipes(recipes);

    }

    public static void GiveStarterItems(PlayerEntity player) {

        if (player.level.isClientSide) {
            return;
        }

        player.inventory.add(new ItemStack(SlashItems.NEWBIE_GEAR_BAG.get()));

    }

}
