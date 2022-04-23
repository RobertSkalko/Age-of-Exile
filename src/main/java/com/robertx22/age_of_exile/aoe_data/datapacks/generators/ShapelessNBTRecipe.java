package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ShapelessNBTRecipe {

    private final ItemStack result;
    private final int count = 1;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    private ResourceLocation key;

    public ShapelessNBTRecipe(ItemStack stack, ResourceLocation key) {
        this.result = stack;
        this.key = key;

        unlockedBy("auto_unlock", autoUnlock());
    }

    protected static InventoryChangeTrigger.Instance autoUnlock() {
        return inventoryTrigger(ItemPredicate.Builder.item()
            .of(Items.AIR)
            .build());
    }

    protected static InventoryChangeTrigger.Instance inventoryTrigger(ItemPredicate... pred) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, pred);
    }

    public static ShapelessNBTRecipe shapeless(ItemStack stack, ResourceLocation key) {
        return new ShapelessNBTRecipe(stack, key);
    }

    public ShapelessNBTRecipe requires(ITag<Item> p_203221_1_) {
        return this.requires(Ingredient.of(p_203221_1_));
    }

    public ShapelessNBTRecipe requires(IItemProvider p_200487_1_) {
        return this.requires(p_200487_1_, 1);
    }

    public ShapelessNBTRecipe requires(IItemProvider p_200491_1_, int p_200491_2_) {
        for (int i = 0; i < p_200491_2_; ++i) {
            this.requires(Ingredient.of(p_200491_1_));
        }

        return this;
    }

    public ShapelessNBTRecipe requires(Ingredient p_200489_1_) {
        return this.requires(p_200489_1_, 1);
    }

    public ShapelessNBTRecipe requires(Ingredient p_200492_1_, int p_200492_2_) {
        for (int i = 0; i < p_200492_2_; ++i) {
            this.ingredients.add(p_200492_1_);
        }

        return this;
    }

    public ShapelessNBTRecipe unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public ShapelessNBTRecipe group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    public void save(Consumer<IFinishedRecipe> finished) {
        this.save(finished, key);
    }

    public void save(Consumer<IFinishedRecipe> finished, String id) {
        if ((new ResourceLocation(id)).equals(key)) {
            throw new IllegalStateException("Shapeless Recipe " + id + " should remove its 'save' argument");
        } else {
            this.save(finished, new ResourceLocation(id));
        }
    }

    public void save(Consumer<IFinishedRecipe> finished, ResourceLocation key) {
        this.ensureValid(key);
        this.advancement.parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
            .rewards(AdvancementRewards.Builder.recipe(key))
            .requirements(IRequirementsStrategy.OR);
        finished.accept(new ShapelessNBTRecipe.Result(key, this.result, this.count, this.group == null ? "" : this.group, this.ingredients, this.advancement, new ResourceLocation(key.getNamespace(), "recipes/" + this.result.getItem()
            .getItemCategory()
            .getRecipeFolderName() + "/" + key.getPath())));
    }

    private void ensureValid(ResourceLocation p_200481_1_) {
        if (this.advancement.getCriteria()
            .isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_200481_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final int count;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation p_i48268_1_, ItemStack p_i48268_2_, int p_i48268_3_, String p_i48268_4_, List<Ingredient> p_i48268_5_, Advancement.Builder p_i48268_6_, ResourceLocation p_i48268_7_) {
            this.id = p_i48268_1_;
            this.result = p_i48268_2_;
            this.count = p_i48268_3_;
            this.group = p_i48268_4_;
            this.ingredients = p_i48268_5_;
            this.advancement = p_i48268_6_;
            this.advancementId = p_i48268_7_;
        }

        public void serializeRecipeData(JsonObject p_218610_1_) {
            if (!this.group.isEmpty()) {
                p_218610_1_.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            p_218610_1_.add("ingredients", jsonarray);
            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", Registry.ITEM.getKey(this.result.getItem())
                .toString());
            if (this.count > 1) {
                resultJson.addProperty("count", this.count);
            }
            if (this.result.getTag() != null) {
                resultJson.addProperty("nbt", NBTDynamicOps.INSTANCE.convertTo(JsonOps.INSTANCE, result.getTag())
                    .toString());
            }

            p_218610_1_.add("result", resultJson);
        }

        public IRecipeSerializer<?> getType() {
            return IRecipeSerializer.SHAPELESS_RECIPE;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}