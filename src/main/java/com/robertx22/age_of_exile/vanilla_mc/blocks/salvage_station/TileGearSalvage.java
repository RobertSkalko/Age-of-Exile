package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.database.data.salvage_recipes.SalvageRecipe;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseSkillStation;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.tile_bases.NonFullBlock;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileGearSalvage extends BaseSkillStation {

    public static List<Integer> INPUT_SLOTS = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17);
    public static List<Integer> FUEL_SLOTS = Arrays.asList(18);

    public static int TOTAL_SLOTS_COUNT = INPUT_SLOTS.size() + FUEL_SLOTS.size() + OUTPUT_SLOTS.size();

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

    public static List<ItemStack> getSmeltingResultForItem(ItemStack st) {

        ISalvagable data = ISalvagable.load(st);

        if (data != null) {
            if (data.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                return data.getSalvageResult(0);
            }
        } else {
            Item item = st.getItem();
            if (item instanceof ISalvagable) {
                ISalvagable sal = (ISalvagable) item;
                if (sal.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                    return sal.getSalvageResult(0);
                }
            }
        }

        return Arrays.asList();

    }

    public TileGearSalvage() {
        super(null, PlayerSkillEnum.NONE, SlashBlockEntities.SALVAGE.get(), TOTAL_SLOTS_COUNT);

    }

    boolean outputsHaveEmptySlots() {
        int emptySlots = 0;

        for (int slot : OUTPUT_SLOTS) {
            if (itemStacks[slot].isEmpty()) {
                emptySlots++;
            }
        }
        return emptySlots > 5;
    }

    private void ouputItems(List<ItemStack> results) {

        List<Integer> outputed = new ArrayList<>();

        if (outputsHaveEmptySlots()) {
            for (int slot : OUTPUT_SLOTS) {
                for (int i = 0; i < results.size(); i++) {
                    ItemStack result = results.get(i);
                    if (!outputed.contains(i)) {
                        if (itemStacks[slot].isEmpty()) {
                            itemStacks[slot] = result;
                            outputed.add(i);
                        } else if (itemStacks[slot].sameItemStackIgnoreDurability(result)) {
                            if ((itemStacks[slot].getCount() + result.getCount()) < result.getItem()
                                .getMaxStackSize()) {
                                itemStacks[slot].setCount(itemStacks[slot].getCount() + result.getCount());
                                outputed.add(i);
                            }
                        }
                    }

                }
            }
        } else {

            Vector3d itempos = new Vector3d(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

            BlockState block = level.getBlockState(worldPosition);

            Direction dir = block.getValue(NonFullBlock.direction);

            itempos = itempos.add(dir.getNormal()
                .getX(), 0, dir.getNormal()
                .getZ());

            for (ItemStack x : results) {
                ItemEntity itemEntity = new ItemEntity(
                    this.level, itempos.x(), itempos.y(), itempos.z(), x);
                itemEntity.setDefaultPickUpDelay();
                this.level.addFreshEntity(itemEntity);
            }
        }
    }

    private boolean salvage() {

        List<ItemStack> stacks = new ArrayList<>();
        for (int inputSlot : INPUT_SLOTS) {
            stacks.add(itemStacks[inputSlot]);
        }

        FilterListWrap<SalvageRecipe> matching = ExileDB.SalvageRecipes()
            .getFilterWrapped(x -> x.matches(stacks));

        if (matching.list.isEmpty()) {
            return noRecipeSalvage(true);
        } else {
            salvageRecipe(matching.list.get(0));
            return true;
        }

    }

    private void salvageRecipe(SalvageRecipe recipe) {

        ResourceLocation loottableId = new ResourceLocation(recipe.loot_table_output);

        LootContext lootContext = new LootContext.Builder((ServerWorld) level)
            .withParameter(LootParameters.TOOL, ItemStack.EMPTY)
            .withParameter(LootParameters.ORIGIN, new Vector3d(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()))
            .withParameter(LootParameters.BLOCK_STATE, Blocks.AIR.defaultBlockState())
            .create(LootParameterSets.BLOCK);
        ServerWorld serverWorld = lootContext.getLevel();
        LootTable lootTable = serverWorld.getServer()
            .getLootTables()
            .get(loottableId);

        List<ItemStack> drops = lootTable.getRandomItems(lootContext);

        for (int inputSlot : INPUT_SLOTS) {
            itemStacks[inputSlot].shrink(1);

        }

        ouputItems(drops);

    }

    private boolean noRecipeSalvage(boolean performSmelt) {

        try {
            List<ItemStack> results;

            for (int inputSlot : INPUT_SLOTS) {
                if (!itemStacks[inputSlot].isEmpty()) {
                    results = getSmeltingResultForItem(itemStacks[inputSlot]);

                    if (!results.isEmpty()) {

                        if (!performSmelt) {
                            return true;
                        }

                        itemStacks[inputSlot].shrink(1);

                        ouputItems(results);
                        return true;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isValidInput(ItemStack stack) {
        return true;
    }

    @Override
    public IFormattableTextComponent getDisplayName() {
        return CLOC.blank("block.mmorpg.salvage_station");
    }

    @Override
    public Container createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearSalvage(num, inventory, this, this.getBlockPos());
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {

        return true;
    }

    @Override
    public void onSmeltTick() {

        PlayerEntity player = getOwner();

        if (player != null) {

            if (getCookProgress() < 1) {
                List<ItemStack> stacks = new ArrayList<>();
                for (int inputSlot : INPUT_SLOTS) {
                    stacks.add(itemStacks[inputSlot]);
                }
                if (stacks.stream()
                    .anyMatch(x -> !x.isEmpty())) {

                    if (!hasFuel()) {
                        burnFuelIfNeeded();
                        cook_ticks--;
                        if (cook_ticks < 0) {
                            cook_ticks = 0;
                        }
                        return;
                    }

                    this.reduceFuel();
                    cook_ticks++;
                } else {
                    cook_ticks--;
                    if (cook_ticks < 0) {
                        cook_ticks = 0;
                    }
                }
                return;
            }

            boolean sal = false;

            if (this.salvage()) {

                cook_ticks = 0;

                sal = true;

            }

            if (sal) {

                SoundUtils.playSound(level, worldPosition, SoundEvents.ANVIL_USE, 0.3F, 1);

                ParticleEnum.sendToClients(
                    worldPosition.above(), level, new ParticlePacketData(worldPosition.above(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.DUST)
                        .amount(15));

                ParticleEnum.sendToClients(
                    worldPosition.above(), level, new ParticlePacketData(worldPosition.above(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.FLAME)
                        .motion(new Vector3d(0, 0, 0))
                        .amount(15));

            }

        } else {
            cook_ticks--;
            if (cook_ticks < 0) {
                cook_ticks = 0;
            }
        }

        if (cook_ticks < 0) {
            cook_ticks = 0;
        }

    }

}