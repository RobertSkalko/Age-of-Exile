package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.player_skills.crafting_inv.ProfCraftContainer;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CraftToolItem extends AutoItem implements IShapedRecipe {

    public PlayerSkillEnum skill;
    public String locname;

    public CraftToolItem(String locname, PlayerSkillEnum skill) {

        super(new Properties().tab(CreativeTabs.Professions)
            .stacksTo(1));
        this.skill = skill;
        this.locname = locname;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        tooltip.add(new StringTextComponent("Used for: ").append(skill.word.locName()));
        tooltip.add(new StringTextComponent("Right click to Open Crafting Menu.").withStyle(TextFormatting.LIGHT_PURPLE));

        if (skill.isGearCraftingProf()) {
            tooltip.add(new StringTextComponent("Requires a matching piece of gear."));
        }

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
                    return new ProfCraftContainer(stack, syncId, inv);
                }
            });

        }

        return ActionResult.success(user.getItemInHand(hand));
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return "craft_tools/" + skill.id;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {

        Item item = skill.getToolCraftItem();

        return shaped(this)
            .define('t', item)
            .define('v', SlashItems.T0_DUST())
            .pattern(" v ")
            .pattern("vtv")
            .pattern(" v ")
            .unlockedBy("player_level", trigger());
    }

}
