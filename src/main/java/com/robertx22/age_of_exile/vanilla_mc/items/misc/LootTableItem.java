package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class LootTableItem extends Item implements IAutoModel {

    public LootTableItem() {
        super(new Settings().maxCount(1)
            .group(CreativeTabs.MyModTab));
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public static ItemStack of(Identifier loottable) {

        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.LOOT_TABLE_ITEM);
        stack.setTag(new CompoundTag());
        stack.getTag()
            .putString("loot_table", loottable.toString());
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {

                ItemStack stack = player.getStackInHand(hand);
                stack.decrement(1);

                Identifier loottableId = new Identifier(stack.getTag()
                    .getString("loot_table"));

                LootContext lootContext = new LootContext.Builder((ServerWorld) world)
                    .parameter(LootContextParameters.THIS_ENTITY, player)
                    .parameter(LootContextParameters.ORIGIN, player.getPos())
                    .parameter(LootContextParameters.TOOL, ItemStack.EMPTY)
                    .parameter(LootContextParameters.BLOCK_STATE, Blocks.AIR.getDefaultState())
                    .build(LootContextTypes.BLOCK);
                ServerWorld serverWorld = lootContext.getWorld();
                LootTable lootTable = serverWorld.getServer()
                    .getLootManager()
                    .getTable(loottableId);

                List<ItemStack> drops = lootTable.generateLoot(lootContext);

                spawnEffects(world, player);

                drops.forEach(x -> player.dropItem(x, false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

    private void spawnEffects(World world, LivingEntity en) {
        FireworkRocketEntity firework = new FireworkRocketEntity(world, en.getX(), en.getY(), en.getZ(), ItemStack.EMPTY);
        firework.setPos(en.getX(), en.getY(), en.getZ());
        WorldUtils.spawnEntity(world, firework);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        try {
            tooltip.add(Words.ClickToOpen.locName()
                .formatted(Formatting.RED));
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

}



