package com.robertx22.age_of_exile.database.data.favor;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class FavorRank implements JsonExileRegistry<FavorRank>, IAutoGson<FavorRank>, IAutoLocName {

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

    public float extra_item_favor_cost = 1;
    public int extra_items_per_boss = 0;
    public int extra_items_per_chest = 0;

    public boolean can_salvage_loot = true;
    public float exp_multi = 1;

    public float favor_drain_per_item = 1;// THIS CAN A PROBLEM. THIS CAN BE GAMED WITH MAGIC FIND!!!!!

    public String text_format = TextFormatting.GREEN.getName();

    public List<String> excludedRarities = new ArrayList<>();

    public transient String locname = "";

    public TextFormatting textFormatting() {
        try {
            return TextFormatting.getByName(text_format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextFormatting.GRAY;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/favor/" + GUID() + ".png");
    }

    public List<IFormattableTextComponent> getTooltip() {
        List<IFormattableTextComponent> list = new ArrayList<>();

        list.add(Words.Favor.locName()
            .withStyle(textFormatting())
            .append(": ")
            .append(locName().withStyle(textFormatting())));

        list.add(new StringTextComponent(""));

        boolean hasBad = false;

        if (!can_salvage_loot) {
            list.add(new StringTextComponent("Looted gear can't be salvaged!").withStyle(TextFormatting.RED)
                .withStyle(TextFormatting.BOLD));
            hasBad = true;
        }
        if (exp_multi < 1) {
            list.add(new StringTextComponent("You gain reduced experience.").withStyle(TextFormatting.RED)
                .withStyle(TextFormatting.BOLD));
            hasBad = true;
        } else if (exp_multi > 1) {
            list.add(new StringTextComponent("You gain increased experience.").withStyle(TextFormatting.AQUA)
                .withStyle(TextFormatting.BOLD));
        }

        if (!drop_unique_gears) {
            list.add(new StringTextComponent("No Unique Gear Drops.").withStyle(TextFormatting.RED));
            hasBad = true;
        }
        if (!drop_currency) {
            list.add(new StringTextComponent("No Currency Drops.").withStyle(TextFormatting.RED));
            hasBad = true;
        }
        if (!drop_gems) {
            list.add(new StringTextComponent("No Gem Drops.").withStyle(TextFormatting.RED));
            hasBad = true;
        }
        if (!drop_runes) {
            list.add(new StringTextComponent("No Rune Drops.").withStyle(TextFormatting.RED));
            hasBad = true;
        }

        if (!excludedRarities.isEmpty()) {
            hasBad = true;
            excludedRarities.forEach(x -> {
                list.add(ExileDB.GearRarities()
                    .get(x)
                    .locName()
                    .append(" rarity can't drop.")
                    .withStyle(TextFormatting.GOLD));
            });
        }

        if (!hasBad) {
            list.add(new StringTextComponent("You are blessed by Azuna").withStyle(TextFormatting.GREEN));
        } else {
            list.add(new StringTextComponent(""));
            list.add(new StringTextComponent("Azuna is disappointed in you.").withStyle(TextFormatting.RED));
        }

        if (extra_items_per_boss > 0) {
            list.add(new StringTextComponent(""));
            list.add(new StringTextComponent("Bosses Drop extra items.").withStyle(TextFormatting.LIGHT_PURPLE));
        }
        if (extra_items_per_chest > 0) {
            list.add(new StringTextComponent("Loot Chests Drop extra items.").withStyle(TextFormatting.LIGHT_PURPLE));
        }

        list.add(new StringTextComponent(""));
        list.add(new StringTextComponent("Restore favor by looting chests found in the world.").withStyle(TextFormatting.BLUE));

        return list;

    }

    public FavorRank(String id) {
        this.id = id;
    }

    @Override
    public Class<FavorRank> getClassForSerialization() {
        return FavorRank.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.FAVOR_RANK;
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
        return SlashRef.MODID + ".favor." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
