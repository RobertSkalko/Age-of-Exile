package com.robertx22.age_of_exile.database.data.tiers.base;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class Difficulty implements JsonExileRegistry<Difficulty>, IAutoGson<Difficulty>, IAutoLocName {

    public static ISerializable<Difficulty> SERIALIZER = new Difficulty();

    public String id = "";
    public float hp_multi = 1;
    public float dmg_multi = 1;
    public float stat_multi = 1;

    public float loot_multi = 1;
    public float higher_rar_chance = 0;

    public int req_player_lvl = 0;

    public int deaths_allowed = -1;
    public int death_favor_penalty = 0;

    public int rank = 0;
    public transient String locname = "";

    public static Difficulty fromRank(int therank) {
        return ExileDB.Difficulties()
            .getList()
            .stream()
            .filter(x -> x.rank == therank)
            .findAny()
            .get();

    }

    @Override
    public Class<Difficulty> getClassForSerialization() {
        return Difficulty.class;
    }

    public List<ITextComponent> getTooltip() {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Difficulty: ").append(locName())
            .withStyle(TextFormatting.DARK_RED));

        list.add(new StringTextComponent(""));

        list.add(new StringTextComponent("Mob Health: " + (int) (hp_multi * 100) + "%").withStyle(TextFormatting.RED));
        list.add(new StringTextComponent("Mob Damage: " + (int) (dmg_multi * 100) + "%").withStyle(TextFormatting.RED));
        list.add(new StringTextComponent("Mob Stats: " + (int) (stat_multi * 100) + "%").withStyle(TextFormatting.RED));

        list.add(new StringTextComponent(""));

        list.add(new StringTextComponent("Loot: " + (int) (loot_multi * 100) + "%").withStyle(TextFormatting.GOLD));

        return list;
    }

    public Difficulty() {

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.TIER;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".difficulty." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
