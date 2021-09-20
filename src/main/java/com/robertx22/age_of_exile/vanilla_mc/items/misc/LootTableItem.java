package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class LootTableItem extends Item implements IAutoModel {

    public LootTableItem() {
        super(new Properties().stacksTo(1)
            .tab(CreativeTabs.MyModTab));
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public static ItemStack of(ResourceLocation loottable) {

        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.LOOT_TABLE_ITEM);
        stack.setTag(new CompoundNBT());
        stack.getTag()
            .putString("loot_table", loottable.toString());
        return stack;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClientSide) {
            try {

                ItemStack stack = player.getItemInHand(hand);
                stack.shrink(1);

                ResourceLocation loottableId = new ResourceLocation(stack.getTag()
                    .getString("loot_table"));

                LootContext lootContext = new LootContext.Builder((ServerWorld) world)
                    .withParameter(LootParameters.THIS_ENTITY, player)
                    .withParameter(LootParameters.ORIGIN, player.position())
                    .withParameter(LootParameters.TOOL, ItemStack.EMPTY)
                    .withParameter(LootParameters.BLOCK_STATE, Blocks.AIR.defaultBlockState())
                    .create(LootParameterSets.BLOCK);
                ServerWorld serverWorld = lootContext.getLevel();
                LootTable lootTable = serverWorld.getServer()
                    .getLootTables()
                    .get(loottableId);

                List<ItemStack> drops = lootTable.getRandomItems(lootContext);

                spawnEffects(world, player);

                drops.forEach(x -> player.drop(x, false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    private void spawnEffects(World world, LivingEntity en) {
        FireworkRocketEntity firework = new FireworkRocketEntity(world, en.getX(), en.getY(), en.getZ(), ItemStack.EMPTY);
        firework.setPosRaw(en.getX(), en.getY(), en.getZ());
        WorldUtils.spawnEntity(world, firework);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        try {
            tooltip.add(Words.ClickToOpen.locName()
                .withStyle(TextFormatting.RED));
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

}



