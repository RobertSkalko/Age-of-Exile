package com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import javax.annotation.Nullable;
import java.util.List;

public class MaterialBagItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    public MaterialBagItem() {
        super(new Properties().tab(CreativeTabs.Professions)
            .fireResistant()
            .stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.startUsingItem(hand);

        if (world != null && !world.isClientSide) {

            ItemStack stack = user.getItemInHand(hand);

            user.openMenu(new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new StringTextComponent("");
                }

                @Nullable
                @Override
                public Container createMenu(int syncId, PlayerInventory inv, PlayerEntity p) {
                    ItemStack stack = p.getMainHandItem();
                    return new MatBagContainer(stack, syncId, inv);
                }
            });

        }

        return ActionResult.success(user.getItemInHand(hand));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        try {

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
        return "Material Pouch";
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
            .define('d', Items.BREAD)
            .pattern("ooo")
            .pattern("omo")
            .pattern("odo")
            .unlockedBy("player_level", trigger());
    }

}
