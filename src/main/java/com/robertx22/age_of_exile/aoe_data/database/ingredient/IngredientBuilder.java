package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.IngredientItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.registry.DataGenKey;

import java.util.Arrays;

public class IngredientBuilder {

    private SlashIngredient ingredient = new SlashIngredient();

    public static IngredientBuilder of(String id, String name, String rarity, RegObj<IngredientItem> item) {
        return of(id, name, rarity, item.get());
    }

    public static IngredientBuilder of(String id, String name, String rarity, IngredientItem item) {
        IngredientBuilder b = new IngredientBuilder();
        b.ingredient.id = id;
        b.ingredient.locname = name;
        b.ingredient.weight = ExileDB.GearRarities()
            .getFromSerializables(new DataGenKey<>(rarity))
            .Weight();
        b.ingredient.rar = rarity;
        b.ingredient.item_id = item.getRegistryName()
            .toString();
        return b;
    }

    public IngredientBuilder allowedIn(PlayerSkillEnum skill) {
        this.ingredient.allowed_in.add(skill.GUID());
        return this;
    }

    public IngredientBuilder stats(StatModifier... stats) {
        this.ingredient.stats.addAll(Arrays.asList(stats));
        return this;
    }

    public SlashIngredient build() {
        ingredient.addToSerializables();
        return ingredient;
    }
}

