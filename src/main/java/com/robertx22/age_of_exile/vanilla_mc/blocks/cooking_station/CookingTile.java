package com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station;

import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.IAutomatable;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.ISmeltingStation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;

public class CookingTile extends BaseModificationStation implements IAutomatable, ISmeltingStation {

    public static List<Integer> INPUT_SLOTS = Arrays.asList(0, 1, 2);
    public static List<Integer> FUEL_SLOTS = Arrays.asList(3);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(4);

    public static int totalSlots() {
        return INPUT_SLOTS.size() + FUEL_SLOTS.size() + OUTPUT_SLOTS.size();
    }

    public CookingTile() {
        super(ModRegistry.BLOCK_ENTITIES.COOKING, totalSlots());

    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        return false;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("");
    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new CookingContainer(num, inventory, this, this.getPos());
    }

    @Override
    public void onSmeltTick() {

        PlayerEntity player = getOwner();

        if (player != null) {

            ItemStack[] craftinv = new ItemStack[INPUT_SLOTS.size()];

            int i = 0;
            for (Integer INPUT_SLOT : INPUT_SLOTS) {
                craftinv[i] = itemStacks[INPUT_SLOT];
                i++;
            }

            Inventory inv = new SimpleInventory(craftinv);

            Recipe<Inventory> recipe = this.world.getRecipeManager()
                .getFirstMatch(ModRegistry.RECIPE_TYPES.FOOD_RECIPE, inv, this.world)
                .orElse(null);

            if (recipe != null) {

                ItemStack output = recipe.craft(inv);

                CraftingReq req = null;

                String key = Registry.ITEM.getId(output.getItem())
                    .toString();

                if (Database.ItemCraftReq()
                    .isRegistered(key)) {
                    req = Database.ItemCraftReq()
                        .get(key);
                }

                if (req != null) {
                    if (!req.meets(player)) {
                        return;
                    }
                }

                for (Integer x : OUTPUT_SLOTS) {
                    if (canPushTo(x, output)) {

                        INPUT_SLOTS.stream()
                            .forEach(e -> {
                                itemStacks[e].decrement(1);
                            });

                        pushTo(x, output);
                    }
                }
            }

        }

    }

    @Override
    public List<Integer> getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public List<Integer> getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public List<Integer> getFuelSlots() {
        return FUEL_SLOTS;
    }
}
