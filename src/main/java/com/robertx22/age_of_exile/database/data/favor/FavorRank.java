package com.robertx22.age_of_exile.database.data.favor;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FavorRank implements ISerializedRegistryEntry<FavorRank>, IAutoGson<FavorRank>, IAutoLocName {

    public static FavorRank SERIALIZER = new FavorRank("");

    String id = "";

    // excludes min and max
    public int min = 0;

    public int rank = 0;

    public boolean drop_unique_gears = true;
    public boolean drop_runes = true;
    public boolean drop_gems = true;
    public boolean drop_currency = true;
    public boolean drop_lvl_rewards = true;
    public boolean drop_exp = true;

    public float extra_item_favor_cost = 1;
    public int extra_items_per_boss = 0;
    public int extra_items_per_chest = 0;

    public boolean can_salvage_loot = true;

    public float favor_drain_per_item = 1;// TODO THIS IS A PROBLEM. THIS CAN BE GAMED WITH MAGIC FIND!!!!!

    public Formatting text_format = Formatting.GREEN;

    public transient String locname = "";

    public Identifier getTexture() {
        return new Identifier(Ref.MODID, "textures/gui/favor/" + GUID() + ".png");
    }

    public List<MutableText> getTooltip() {
        List<MutableText> list = new ArrayList<>();

        list.add(Words.Favor.locName()
            .formatted(text_format)
            .append(": ")
            .append(locName().formatted(text_format)));

        list.add(new LiteralText(""));

        boolean hasBad = false;

        if (!can_salvage_loot) {
            list.add(new LiteralText("Looted gear can't be salvaged!").formatted(Formatting.RED)
                .formatted(Formatting.BOLD));
            hasBad = true;
        }
        if (!can_salvage_loot) {
            list.add(new LiteralText("You can't gain experience").formatted(Formatting.RED)
                .formatted(Formatting.BOLD));
            hasBad = true;
        }

        if (!drop_unique_gears) {
            list.add(new LiteralText("No Unique Gear Drops.").formatted(Formatting.RED));
            hasBad = true;
        }
        if (!drop_currency) {
            list.add(new LiteralText("No Currency Drops.").formatted(Formatting.RED));
            hasBad = true;
        }
        if (!drop_gems) {
            list.add(new LiteralText("No Gem Drops.").formatted(Formatting.RED));
            hasBad = true;
        }
        if (!drop_runes) {
            list.add(new LiteralText("No Rune Drops.").formatted(Formatting.RED));
            hasBad = true;
        }

        if (!excludedRarities.isEmpty()) {
            hasBad = true;
            excludedRarities.forEach(x -> {
                list.add(SlashRegistry.GearRarities()
                    .get(x)
                    .locName()
                    .append(" rarity can't drop.")
                    .formatted(Formatting.GOLD));
            });
        }

        if (!hasBad) {
            list.add(new LiteralText("You are blessed by Azuna").formatted(Formatting.GREEN));
        } else {
            list.add(new LiteralText(""));
            list.add(new LiteralText("Azuna is disappointed in you.").formatted(Formatting.RED));
        }

        if (extra_items_per_boss > 0) {
            list.add(new LiteralText(""));
            list.add(new LiteralText("Bosses Drop extra items.").formatted(Formatting.LIGHT_PURPLE));
        }
        if (extra_items_per_chest > 0) {
            list.add(new LiteralText("Loot Chests Drop extra items.").formatted(Formatting.LIGHT_PURPLE));
        }

        list.add(new LiteralText(""));
        list.add(new LiteralText("Restore favor by looting chests found in the world.").formatted(Formatting.BLUE));

        return list;

    }

    public List<String> excludedRarities = new ArrayList<>();

    public FavorRank(String id) {
        this.id = id;
    }

    @Override
    public Class<FavorRank> getClassForSerialization() {
        return FavorRank.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.FAVOR_RANK;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".favor." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
