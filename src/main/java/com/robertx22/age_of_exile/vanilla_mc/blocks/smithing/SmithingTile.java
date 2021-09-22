package com.robertx22.age_of_exile.vanilla_mc.blocks.smithing;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeTypes;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseSkillStation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import java.util.Arrays;
import java.util.List;

public class SmithingTile extends BaseSkillStation {

    public static List<Integer> INPUT_SLOTS = Arrays.asList(0, 1, 2);
    public static List<Integer> FUEL_SLOTS = Arrays.asList(3);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(4);

    public static int totalSlots() {
        return INPUT_SLOTS.size() + FUEL_SLOTS.size() + OUTPUT_SLOTS.size();
    }

    public SmithingTile() {
        super(SlashRecipeTypes.SMITHING_RECIPE, PlayerSkillEnum.BLACKSMITHING, SlashBlockEntities.SMITHING_STATION.get(), totalSlots());
    }

    @Override
    public Container createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new SmithingContainer(num, inventory, this, this.getBlockPos());
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
