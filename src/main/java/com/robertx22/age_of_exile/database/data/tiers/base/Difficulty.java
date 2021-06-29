package com.robertx22.age_of_exile.database.data.tiers.base;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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

    @Override
    public Class<Difficulty> getClassForSerialization() {
        return Difficulty.class;
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Difficulty: ").append(locName())
            .formatted(Formatting.DARK_RED));

        list.add(new LiteralText(""));

        list.add(new LiteralText("Mob Health: " + (int) (hp_multi * 100) + "%").formatted(Formatting.RED));
        list.add(new LiteralText("Mob Damage: " + (int) (dmg_multi * 100) + "%").formatted(Formatting.RED));
        list.add(new LiteralText("Mob Stats: " + (int) (stat_multi * 100) + "%").formatted(Formatting.RED));

        list.add(new LiteralText(""));

        list.add(new LiteralText("Loot: " + (int) (loot_multi * 100) + "%").formatted(Formatting.GOLD));

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
        return Ref.MODID + ".difficulty." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
