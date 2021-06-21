package com.robertx22.age_of_exile.database.data.tiers.base;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class Tier implements JsonExileRegistry<Tier>, IAutoGson<Tier> {

    public static ISerializable<Tier> SERIALIZER = new Tier(1);

    public float hp_multi = 1;
    public float dmg_multi = 1;
    public float stat_multi = 1;

    public float loot_multi = 1;
    public float higher_rar_chance = 0;

    public int id_rank = 0;

    @Override
    public Class<Tier> getClassForSerialization() {
        return Tier.class;
    }

    public Tier(int id_rank) {
        this.id_rank = id_rank;

        this.setLootPerRank();
        this.setMobStrengthPerRank();
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Difficulty: Tier " + id_rank).formatted(Formatting.DARK_RED));

        list.add(new LiteralText(""));

        list.add(new LiteralText("Mob Health: " + (int) (hp_multi * 100) + "%").formatted(Formatting.RED));
        list.add(new LiteralText("Mob Damage: " + (int) (dmg_multi * 100) + "%").formatted(Formatting.RED));
        list.add(new LiteralText("Mob Stats: " + (int) (stat_multi * 100) + "%").formatted(Formatting.RED));

        list.add(new LiteralText(""));

        list.add(new LiteralText("Loot: " + (int) (loot_multi * 100) + "%").formatted(Formatting.GOLD));

        return list;
    }

    private Tier() {

    }

    protected void setMobStrengthPerRank() {
        this.hp_multi = 1F + 0.08F * id_rank;
        this.dmg_multi = 1F + 0.04F * id_rank;
        this.stat_multi = 1F + 0.01F * id_rank;
    }

    protected void setLootPerRank() {
        this.loot_multi = 1F + 0.01F * id_rank;
        this.higher_rar_chance = id_rank * 0.1F;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.TIER;
    }

    @Override
    public String GUID() {
        return id_rank + "";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
