package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ItemUtils {
    public static Item.Properties getDefaultGearProperties() {

        Item.Properties prop = new Item.Properties().tab(CreativeTabs.MyModTab);

        return prop;
    }

    public static void tryAnnounceItem(ItemStack stack, PlayerEntity player) {

        try {
            if (player == null) {
                return;
            }
            if (!ModConfig.get().Server.ENABLE_LOOT_ANNOUNCEMENTS) {
                return;
            }

            GearItemData gear = Gear.Load(stack);

            if (gear != null) {

                GearRarity rar = (GearRarity) gear.getRarity();

                if (rar.announce_in_chat) {

                    IFormattableTextComponent name = new StringTextComponent("").append(player.getName())
                        .withStyle(TextFormatting.BOLD)
                        .withStyle(TextFormatting.LIGHT_PURPLE);

                    ITextComponent txt = name
                        .append(" found a ")
                        .append(rar.locName()
                            .withStyle(rar.textFormatting())
                            .withStyle(TextFormatting.BOLD))
                        .append(" item!");

                    player.getServer()
                        .getPlayerList()
                        .getPlayers()
                        .forEach(x -> x.displayClientMessage(txt, false));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
