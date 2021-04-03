package com.robertx22.age_of_exile.player_skills.recipe_types.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class StationShapeless implements Recipe<Inventory> {
    public final Identifier id;
    public final String group;
    public final ItemStack output;
    public final DefaultedList<Ingredient> input;

    public StationShapeless(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        this.id = id;
        this.group = group;
        this.output = output;
        this.input = input;
    }

    public Identifier getId() {
        return this.id;
    }

    @Environment(EnvType.CLIENT)
    public String getGroup() {
        return this.group;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public DefaultedList<Ingredient> getPreviewInputs() {
        return this.input;
    }

    public boolean matches(Inventory craftingInventory, World world) {
        RecipeFinder recipeFinder = new RecipeFinder();
        int i = 0;

        for (int j = 0; j < craftingInventory.size(); ++j) {
            ItemStack itemStack = craftingInventory.getStack(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeFinder.method_20478(itemStack, 1);
            }
        }

        return i == this.input.size() && recipeFinder.findRecipe(this, (IntList) null);
    }

    public ItemStack craft(Inventory craftingInventory) {
        return this.output.copy();
    }

    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return width * height >= this.input.size();
    }

    public abstract static class Serializer<T extends StationShapeless> implements RecipeSerializer<T> {
        @Override
        public T read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for station shapeless recipe");
            } else if (defaultedList.size() > 3) {
                throw new JsonParseException("Too many ingredients for station shapeless recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
                return createNew(identifier, string, itemStack, defaultedList);
            }
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();

            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        public abstract T createNew(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input);

        @Override
        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString(32767);
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);

            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return createNew(identifier, string, itemStack, defaultedList);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, T FoodShapeless) {
            packetByteBuf.writeString(FoodShapeless.group);
            packetByteBuf.writeVarInt(FoodShapeless.input.size());
            Iterator var3 = FoodShapeless.input.iterator();

            while (var3.hasNext()) {
                Ingredient ingredient = (Ingredient) var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(FoodShapeless.output);
        }
    }
}
