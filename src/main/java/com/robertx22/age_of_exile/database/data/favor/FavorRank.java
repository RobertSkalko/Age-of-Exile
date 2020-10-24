package com.robertx22.age_of_exile.database.data.favor;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FavorRank implements ISerializedRegistryEntry<FavorRank>, IAutoGson<FavorRank> {

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

    public boolean can_salvage_loot = true;

    public float favor_drain_per_item = 1;// TODO THIS IS A PROBLEM. THIS CAN BE GAMED WITH MAGIC FIND!!!!!

    public List<MutableText> getTooltip() {
        List<MutableText> list = new ArrayList<>();

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
            list.add(new LiteralText("Azuna is disappointed in you.").formatted(Formatting.LIGHT_PURPLE));
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
}
