package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.blocks.IAutomatable;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public abstract class BaseSkillStation extends BaseModificationStation implements IAutomatable, ISmeltingStation {

    PlayerSkillEnum skill;
    RecipeType<? extends Recipe<Inventory>> recipeType;

    public BaseSkillStation(RecipeType<? extends Recipe<Inventory>> recipeType, PlayerSkillEnum skill, BlockEntityType<?> type, int slots) {
        super(type, slots);
        this.skill = skill;
        this.recipeType = recipeType;
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        return false;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("");
    }

    public float getCookProgress() {
        return cook_ticks / (20F * 8F); // todo setup cook time in recipe
    }

    public float getFuelProgress() {
        return fuel / 2000F;
    }

    public void burnFuelIfNeeded() {
        if (!hasFuel()) {

            getFuelSlots().forEach(x -> {
                ItemStack stack = itemStacks[x];

                int val = FurnaceBlockEntity.createFuelTimeMap()
                    .getOrDefault(stack.getItem(), 0);

                if (val > 0) {
                    stack.decrement(1);
                    fuel += val;
                }
            });

        }
    }

    public boolean hasFuel() {
        if (!needsFuel()) {
            return true;
        }
        return fuel > 0;
    }

    public boolean needsFuel() {
        return !getFuelSlots().isEmpty();
    }

    public Recipe<Inventory> getRecipeBeingTried() {

        Inventory inv = getCraftingInventory();

        Recipe<Inventory> recipe = this.world.getRecipeManager()
            .getFirstMatch(recipeType, inv, this.world)
            .orElse(null);

        return recipe;
    }

    public Inventory getCraftingInventory() {
        ItemStack[] craftinv = new ItemStack[getInputSlots().size()];
        int i = 0;
        for (Integer INPUT_SLOT : getInputSlots()) {
            craftinv[i] = itemStacks[INPUT_SLOT];
            i++;
        }
        Inventory inv = new SimpleInventory(craftinv);

        return inv;
    }

    public ItemStack getStackBeingCrafted() {
        Recipe<Inventory> recipe = getRecipeBeingTried();
        if (recipe != null) {
            return recipe.getOutput();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public void reduceFuel() {
        fuel--;
        if (fuel < 0) {
            fuel = 0;
        }
    }

    @Override
    public void onSmeltTick() {

        reduceFuel();

        PlayerEntity player = getOwner();

        if (player != null) {

            Inventory inv = getCraftingInventory();

            Recipe<Inventory> recipe = getRecipeBeingTried();

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
                        cook_ticks--;
                        if (cook_ticks < 0) {
                            cook_ticks = 0;
                        }
                        return;
                    }
                }

                burnFuelIfNeeded();

                if (!hasFuel()) {
                    cook_ticks--;
                    if (cook_ticks < 0) {
                        cook_ticks = 0;
                    }
                    return;
                }

                for (Integer x : getOutputSlots()) {
                    if (canPushTo(x, output)) {

                        if (getCookProgress() < 1F) {
                            cook_ticks++;
                            return;
                        } else {
                            cook_ticks = 0;
                        }

                        this.expEarned += 1; // todo

                        getInputSlots().stream()
                            .forEach(e -> {
                                itemStacks[e].decrement(1);
                            });

                        PlayerSkill skill = Database.PlayerSkills()
                            .get(this.skill.id);

                        PlayerSkills skills = Load.playerSkills(player);

                        int exp = skill.getExpForCraft(output, player);

                        skills.addExp(skill.type_enum, exp);

                        pushTo(x, output);
                    }
                }
            } else {
                cook_ticks--;
                if (cook_ticks < 0) {
                    cook_ticks = 0;
                }
            }
        }

    }

}
