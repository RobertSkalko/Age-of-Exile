package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DungeonKeyItem extends AutoItem {

    public DungeonKeyItem(KeyRarity keyRarity) {
        super(new Settings().maxCount(1)
            .group(CreativeTabs.MyModTab));
        this.keyRarity = keyRarity;
    }

    public KeyRarity keyRarity = KeyRarity.Common;

    public enum KeyRarity {
        Common(Formatting.GRAY, "common", "Common", 1F, 3),
        Rare(Formatting.YELLOW, "rare", "Rare", 1.25F, 6),
        Epic(Formatting.LIGHT_PURPLE, "epic", "Epic", 1.5F, 10);

        public String id;
        public String locname;
        public float lootmulti;
        public Formatting format;
        public int maxDungeons;

        KeyRarity(Formatting format, String id, String locname, float lootmulti, int maxDungeons) {
            this.id = id;
            this.format = format;
            this.locname = locname;
            this.maxDungeons = maxDungeons;
            this.lootmulti = lootmulti;
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(keyRarity.format);
    }

    public int getLootMultiPercent() {

        return (int) (keyRarity.lootmulti * 100);
    }

    @Override
    public String locNameForLangFile() {
        return keyRarity.locname + " Dungeon Key";
    }

    @Override
    public String GUID() {
        return "dungeon_key/" + keyRarity.id;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Words.Loot.locName()
            .append(": " + getLootMultiPercent() + "%")
            .formatted(Formatting.GOLD));

    }

}
