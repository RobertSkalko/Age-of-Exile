package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class VanillaExperienceChestItem extends AutoItem {

    public VanillaExperienceChestItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));
    }

    @Override
    public String GUID() {
        return "potions/add_reset_perk_points";
    }

    static List<Integer> LIST = Arrays.asList(50, 100, 250);

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (int num : LIST) {
                ItemStack stack = new ItemStack(this);
                stack.setTag(new CompoundTag());
                stack.getTag()
                    .putInt("exp", num);
                stacks.add(stack);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getStackInHand(handIn);

        try {
            if (player instanceof PlayerEntity) {
                stack.decrement(1);
                PlayerEntity p = player;
                int exp = stack.getTag()
                    .getInt("exp");
                p.addExperience(exp);
                SoundUtils.ding(worldIn, player.getBlockPos());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TypedActionResult.success(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Enchanting Experience Chest";
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        try {
            tooltip.add(Words.Exp.locName()
                .formatted(Formatting.GREEN)
                .append(": " + stack.getTag()
                    .getInt("exp")));
        } catch (Exception e) {
        }
    }

}
