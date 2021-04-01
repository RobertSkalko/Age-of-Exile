package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.salvage_recipes.SalvageRecipe;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseSkillStation;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.tile_bases.NonFullBlock;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

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

        ICommonDataItem data = ICommonDataItem.load(st);

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
        super(null, PlayerSkillEnum.SALVAGING, ModRegistry.BLOCK_ENTITIES.GEAR_SALVAGE, TOTAL_SLOTS_COUNT);

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
                        } else if (itemStacks[slot].isItemEqual(result)) {
                            if ((itemStacks[slot].getCount() + result.getCount()) < result.getItem()
                                .getMaxCount()) {
                                itemStacks[slot].setCount(itemStacks[slot].getCount() + result.getCount());
                                outputed.add(i);
                            }
                        }
                    }

                }
            }
        } else {

            Vec3d itempos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

            BlockState block = world.getBlockState(pos);

            Direction dir = block.get(NonFullBlock.direction);

            itempos = itempos.add(dir.getVector()
                .getX(), 0, dir.getVector()
                .getZ());

            for (ItemStack x : results) {
                ItemEntity itemEntity = new ItemEntity(
                    this.world, itempos.getX(), itempos.getY(), itempos.getZ(), x);
                itemEntity.setToDefaultPickupDelay();
                this.world.spawnEntity(itemEntity);
            }
        }
    }

    private boolean salvage() {

        List<ItemStack> stacks = new ArrayList<>();
        for (int inputSlot : INPUT_SLOTS) {
            stacks.add(itemStacks[inputSlot]);
        }

        FilterListWrap<SalvageRecipe> matching = Database.SalvageRecipes()
            .getFilterWrapped(x -> x.matches(stacks));

        if (matching.list.isEmpty()) {
            return noRecipeSalvage(true);
        } else {
            salvageRecipe(matching.list.get(0));
            return true;
        }

    }

    private void salvageRecipe(SalvageRecipe recipe) {

        Identifier loottableId = new Identifier(recipe.loot_table_output);

        LootContext lootContext = new LootContext.Builder((ServerWorld) world)
            .parameter(LootContextParameters.TOOL, ItemStack.EMPTY)
            .parameter(LootContextParameters.ORIGIN, new Vec3d(pos.getX(), pos.getY(), pos.getZ()))
            .parameter(LootContextParameters.BLOCK_STATE, Blocks.AIR.getDefaultState())
            .build(LootContextTypes.BLOCK);
        ServerWorld serverWorld = lootContext.getWorld();
        LootTable lootTable = serverWorld.getServer()
            .getLootManager()
            .getTable(loottableId);

        List<ItemStack> drops = lootTable.generateLoot(lootContext);

        for (int inputSlot : INPUT_SLOTS) {
            itemStacks[inputSlot] = ItemStack.EMPTY;
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

                        itemStacks[inputSlot] = ItemStack.EMPTY;

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
        return this.getSmeltingResultForItem(stack)
            .stream()
            .anyMatch(x -> !x.isEmpty());

    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.salvage_station");
    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearSalvage(num, inventory, this, this.getPos());
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {

        return true;
    }

    @Override
    public void onSmeltTick() {

        reduceFuel();

        PlayerEntity player = getOwner();

        if (player != null) {

            if (!hasFuel()) {
                burnFuelIfNeeded();
                cook_ticks--;
                return;
            }

            if (getCookProgress() < 1) {
                List<ItemStack> stacks = new ArrayList<>();
                for (int inputSlot : INPUT_SLOTS) {
                    stacks.add(itemStacks[inputSlot]);
                }
                if (stacks.stream()
                    .anyMatch(x -> !x.isEmpty())) {
                    cook_ticks++;
                }
                return;
            }

            boolean sal = false;

            if (this.salvage()) {

                cook_ticks = 0;

                sal = true;

                PlayerSkill skill = Database.PlayerSkills()
                    .get(PlayerSkillEnum.SALVAGING.id);

                PlayerSkills skills = Load.playerSkills(player);

                int exp = skill.getExpForAction(player);

                skills.addExp(skill.type_enum, exp);

                List<ItemStack> list = skill.getExtraDropsFor(player, exp, LevelUtils.levelToTier(skills.getLevel(skill.type_enum)));

                SkillDropData effect = new SkillDropData(player, PlayerSkillEnum.SALVAGING, list);
                effect.Activate();

                effect.extraDrops.forEach(x -> PlayerUtils.giveItem(x, player));

            }

            if (sal) {

                SoundUtils.playSound(world, pos, SoundEvents.BLOCK_ANVIL_USE, 0.3F, 1);

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.DUST)
                        .amount(15));

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.FLAME)
                        .motion(new Vec3d(0, 0, 0))
                        .amount(15));

            }

        }

        if (cook_ticks < 0) {
            cook_ticks = 0;
        }

    }

}