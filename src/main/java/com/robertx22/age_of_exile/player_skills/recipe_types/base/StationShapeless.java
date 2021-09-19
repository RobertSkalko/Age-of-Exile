package com.robertx22.age_of_exile.player_skills.recipe_types.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.recipe.*;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Iterator;

public abstract class StationShapeless implements Recipe<IInventory> {
    public final ResourceLocation id;
    public final String group;
    public final ItemStack output;
    public final NonNullList<Ingredient> input;

    public StationShapeless(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
        this.id = id;
        this.group = group;
        this.output = output;
        this.input = input;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @OnlyIn(Dist.CLIENT)
    public String getGroup() {
        return this.group;
    }

    public ItemStack getResultItem() {
        return this.output;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.input;
    }

    public boolean matches(IInventory craftingInventory, World world) {
        StackedContents recipeFinder = new StackedContents();
        int i = 0;

        for (int j = 0; j < craftingInventory.getContainerSize(); ++j) {
            ItemStack itemStack = craftingInventory.getItem(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeFinder.accountStack(itemStack, 1);
            }
        }

        return i == this.input.size() && recipeFinder.canCraft(this, (IntList) null);
    }

    public ItemStack assemble(IInventory craftingInventory) {
        return this.output.copy();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.input.size();
    }

    public abstract static class Serializer<T extends StationShapeless> implements IRecipeSerializer<T> {
        @Override
        public T fromJson(ResourceLocation identifier, JsonObject jsonObject) {
            String string = GsonHelper.getAsString(jsonObject, "group", "");
            NonNullList<Ingredient> defaultedList = getIngredients(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for station shapeless recipe");
            } else if (defaultedList.size() > 3) {
                throw new JsonParseException("Too many ingredients for station shapeless recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                return createNew(identifier, string, itemStack, defaultedList);
            }
        }

        private static NonNullList<Ingredient> getIngredients(JsonArray json) {
            NonNullList<Ingredient> defaultedList = NonNullList.create();

            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        public abstract T createNew(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input);

        @Override
        public T fromNetwork(ResourceLocation identifier, PacketBuffer packetByteBuf) {
            String string = packetByteBuf.readUtf(32767);
            int i = packetByteBuf.readVarInt();
            NonNullList<Ingredient> defaultedList = NonNullList.withSize(i, Ingredient.EMPTY);

            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromNetwork(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItem();
            return createNew(identifier, string, itemStack, defaultedList);
        }

        @Override
        public void write(PacketBuffer packetByteBuf, T FoodShapeless) {
            packetByteBuf.writeUtf(FoodShapeless.group);
            packetByteBuf.writeVarInt(FoodShapeless.input.size());
            Iterator var3 = FoodShapeless.input.iterator();

            while (var3.hasNext()) {
                Ingredient ingredient = (Ingredient) var3.next();
                ingredient.toNetwork(packetByteBuf);
            }

            packetByteBuf.writeItem(FoodShapeless.output);
        }
    }
}
