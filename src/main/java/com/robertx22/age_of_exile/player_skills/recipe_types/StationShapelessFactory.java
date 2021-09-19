package com.robertx22.age_of_exile.player_skills.recipe_types;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class StationShapelessFactory {

    private final Item output;
    private final int outputCount;
    private final List<Ingredient> inputs = Lists.newArrayList();
    private final Advancement.Builder builder = Advancement.Builder.advancement();
    private String group;

    IRecipeSerializer<?> recipeSerializer;

    public StationShapelessFactory(IRecipeSerializer<?> recipeSerializer, IItemProvider itemProvider, int outputCount) {
        this.output = itemProvider.asItem();
        this.outputCount = outputCount;
        this.recipeSerializer = recipeSerializer;
    }

    public static StationShapelessFactory create(IRecipeSerializer<?> recipeSerializer, IItemProvider output) {
        return new StationShapelessFactory(recipeSerializer, output, 1);
    }

    public static StationShapelessFactory create(IRecipeSerializer<?> recipeSerializer, IItemProvider output, int outputCount) {
        return new StationShapelessFactory(recipeSerializer, output, outputCount);
    }

    public StationShapelessFactory input(Tag<Item> tag) {
        return this.input(Ingredient.of(tag));
    }

    public StationShapelessFactory input(IItemProvider itemProvider) {
        return this.input((IItemProvider) itemProvider, 1);
    }

    public StationShapelessFactory input(IItemProvider itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.of(itemProvider));
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

    public StationShapelessFactory criterion(String criterionName, ICriterionInstance conditions) {
        this.builder.addCriterion(criterionName, conditions);
        return this;
    }

    public StationShapelessFactory group(String group) {
        this.group = group;
        return this;
    }

    public void offerTo(Consumer<IFinishedRecipe> exporter) {
        this.offerTo(exporter, Registry.ITEM.getKey(this.output));
    }

    public void offerTo(Consumer<IFinishedRecipe> exporter, String recipeIdStr) {
        ResourceLocation identifier = Registry.ITEM.getKey(this.output);
        if ((new ResourceLocation(recipeIdStr)).equals(identifier)) {
            throw new IllegalStateException("Shapeless Recipe " + recipeIdStr + " should remove its 'save' argument");
        } else {
            this.offerTo(exporter, new ResourceLocation(recipeIdStr));
        }
    }

    public void offerTo(Consumer<IFinishedRecipe> exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        this.builder.parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .requirements(IRequirementsStrategy.OR);
        exporter.accept(new StationShapelessProvider(recipeSerializer, recipeId, this.output, this.outputCount, this.group == null ? "" : this.group, this.inputs, this.builder, new ResourceLocation(recipeId.getNamespace(), "recipes/" + this.output.getItemCategory()
            .getRecipeFolderName() + "/" + recipeId.getPath())));
    }

    private void validate(ResourceLocation recipeId) {
        if (this.builder.getCriteria()
            .isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }

    public static class StationShapelessProvider implements IFinishedRecipe {
        private final ResourceLocation recipeId;
        private final Item output;
        private final int count;
        private final String group;
        private final List<Ingredient> inputs;
        private final Advancement.Builder builder;
        private final ResourceLocation advancementId;
        IRecipeSerializer<?> recipeSerializer;

        public StationShapelessProvider(IRecipeSerializer<?> recipeSerializer, ResourceLocation recipeId, Item output, int outputCount, String group, List<Ingredient> inputs, Advancement.Builder builder, ResourceLocation advancementId) {
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
        public void serializeRecipeData(JsonObject json) {
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
            jsonObject.addProperty("item", Registry.ITEM.getKey(this.output)
                .toString());
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return recipeSerializer;
        }

        @Override
        public ResourceLocation getId() {
            return this.recipeId;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.builder.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
