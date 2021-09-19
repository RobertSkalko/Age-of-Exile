package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BagUpgradesData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BackpackItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    public BackpackItem() {
        super(new Properties().tab(CreativeTabs.Professions)
            .fireResistant()
            .stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.startUsingItem(hand);

        if (world != null && !world.isClientSide) {

            ItemStack stack = user.getItemInHand(hand);
            if (!IsSkillItemUsableUtil.canUseItem(user, stack, true)) {
                return ActionResult.fail(stack);
            }

            user.openMenu(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketBuffer packetByteBuf) {
                    packetByteBuf.writeEnum(hand);
                }

                @Override
                public ITextComponent getDisplayName() {
                    return new StringTextComponent("");
                }

                @Override
                public Container createMenu(int syncId, IInventory inv, PlayerEntity player) {
                    ItemStack stack = player.getMainHandItem();
                    return new BackpackContainer(stack, syncId, inv);
                }
            });
        }

        return ActionResult.success(user.getItemInHand(hand));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        try {
            BagUpgradesData data = BagUpgradesData.load(stack);

            data.getUpgrades()
                .forEach(x -> {
                    tooltip.add(x.getName(stack));
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getDescriptionId()).withStyle(TextFormatting.YELLOW);
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Adventurer's Backpack";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
            .define('o', Items.LEATHER)
            .define('m', Items.CHEST)
            .define('d', Items.IRON_INGOT)
            .pattern("ooo")
            .pattern("omo")
            .pattern("odo")
            .unlockedBy("player_level", trigger());
    }

}