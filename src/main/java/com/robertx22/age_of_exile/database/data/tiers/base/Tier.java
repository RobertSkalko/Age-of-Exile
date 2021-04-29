package com.robertx22.age_of_exile.database.data.tiers.base;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class Tier implements ISerializedRegistryEntry<Tier>, IAutoGson<Tier> {

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
        this.hp_multi = 1F + 0.1F * id_rank;
        this.dmg_multi = 1F + 0.025F * id_rank;
        this.stat_multi = 1F + 0.02F * id_rank;
    }

    protected void setLootPerRank() {
        this.loot_multi = 1F + 0.01F * id_rank;
        this.higher_rar_chance = id_rank * 1;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.TIER;
    }

    @Override
    public String GUID() {
        return id_rank + "";
    }

}
