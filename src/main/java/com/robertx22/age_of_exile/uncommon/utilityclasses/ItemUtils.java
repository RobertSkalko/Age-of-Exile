package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ItemUtils {
    public static Item.Settings getDefaultGearProperties() {

        Item.Settings prop = new Item.Settings().group(CreativeTabs.MyModTab);

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

                    MutableText name = new LiteralText("").append(player.getName())
                        .formatted(Formatting.BOLD)
                        .formatted(Formatting.LIGHT_PURPLE);

                    Text txt = name
                        .append(" found a ")
                        .append(rar.locName()
                            .formatted(rar.textFormatting())
                            .formatted(Formatting.BOLD))
                        .append(" item!");

                    player.getServer()
                        .getPlayerManager()
                        .getPlayerList()
                        .forEach(x -> x.sendMessage(txt, false));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
