package com.robertx22.age_of_exile.player_skills.recipe_types;

import blue.endless.jankson.annotation.Nullable;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class StationShapelessFactory {

    private final Item output;
    private final int outputCount;
    private final List<Ingredient> inputs = Lists.newArrayList();
    private final Advancement.Task builder = Advancement.Task.create();
    private String group;

    RecipeSerializer<?> recipeSerializer;

    public StationShapelessFactory(RecipeSerializer<?> recipeSerializer, ItemConvertible itemProvider, int outputCount) {
        this.output = itemProvider.asItem();
        this.outputCount = outputCount;
        this.recipeSerializer = recipeSerializer;
    }

    public static StationShapelessFactory create(RecipeSerializer<?> recipeSerializer, ItemConvertible output) {
        return new StationShapelessFactory(recipeSerializer, output, 1);
    }

    public static StationShapelessFactory create(RecipeSerializer<?> recipeSerializer, ItemConvertible output, int outputCount) {
        return new StationShapelessFactory(recipeSerializer, output, outputCount);
    }

    public StationShapelessFactory input(Tag<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public StationShapelessFactory input(ItemConvertible itemProvider) {
        return this.input((ItemConvertible) itemProvider, 1);
    }

    public StationShapelessFactory input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }

        return this;
    }

    public StationShapelessFactory input(Ingredient ingredient) {
        return this.input((Ingredient) ingredient, 1);
    }

    public StationShapelessFactory input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }

        return this;
    }

    public StationShapelessFactory criterion(String criterionName, CriterionConditions conditions) {
        this.builder.criterion(criterionName, conditions);
        return this;
    }

    public StationShapelessFactory group(String group) {
        this.group = group;
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, Registry.ITEM.getId(this.output));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, String recipeIdStr) {
        Identifier identifier = Registry.ITEM.getId(this.output);
        if ((new Identifier(recipeIdStr)).equals(identifier)) {
            throw new IllegalStateException("Shapeless Recipe " + recipeIdStr + " should remove its 'save' argument");
        } else {
            this.offerTo(exporter, new Identifier(recipeIdStr));
        }
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        this.builder.parent(new Identifier("recipes/root"))
            .criterion("has_the_recipe", (CriterionConditions) RecipeUnlockedCriterion.create(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .criteriaMerger(CriterionMerger.OR);
        exporter.accept(new StationShapelessProvider(recipeSerializer, recipeId, this.output, this.outputCount, this.group == null ? "" : this.group, this.inputs, this.builder, new Identifier(recipeId.getNamespace(), "recipes/" + this.output.getGroup()
            .getName() + "/" + recipeId.getPath())));
    }

    private void validate(Identifier recipeId) {
        if (this.builder.getCriteria()
            .isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }

    public static class StationShapelessProvider implements RecipeJsonProvider {
        private final Identifier recipeId;
        private final Item output;
        private final int count;
        private final String group;
        private final List<Ingredient> inputs;
        private final Advancement.Task builder;
        private final Identifier advancementId;
        RecipeSerializer<?> recipeSerializer;

        public StationShapelessProvider(RecipeSerializer<?> recipeSerializer, Identifier recipeId, Item output, int outputCount, String group, List<Ingredient> inputs, Advancement.Task builder, Identifier advancementId) {
            this.recipeId = recipeId;
            this.output = output;
            this.count = outputCount;
            this.group = group;
            this.inputs = inputs;
            this.builder = builder;
            this.advancementId = advancementId;
            this.recipeSerializer = recipeSerializer;
        }

        @Override
        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            JsonArray jsonArray = new JsonArray();
            Iterator var3 = this.inputs.iterator();

            while (var3.hasNext()) {
                Ingredient ingredient = (Ingredient) var3.next();
                jsonArray.add(ingredient.toJson());
            }

            json.add("ingredients", jsonArray);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registry.ITEM.getId(this.output)
                .toString());
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return recipeSerializer;
        }

        @Override
        public Identifier getRecipeId() {
            return this.recipeId;
        }

        @Override
        @Nullable
        public JsonObject toAdvancementJson() {
            return this.builder.toJson();
        }

        @Override
        @Nullable
        public Identifier getAdvancementId() {
            return this.advancementId;
        }
    }
}
