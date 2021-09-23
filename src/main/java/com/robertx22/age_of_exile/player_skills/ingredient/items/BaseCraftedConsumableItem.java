package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftedConsumableData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftingProcessData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class BaseCraftedConsumableItem extends AutoItem {

    PlayerSkillEnum skill;

    public BaseCraftedConsumableItem(PlayerSkillEnum skill) {
        super(new Item.Properties());
        this.skill = skill;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return ActionResult.success(itemStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity en) {

        if (en instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) en;

            CraftedConsumableData data = StackSaving.CRAFTED_CONSUMABLE.loadFrom(stack);

            if (data != null) {
                if (!data.canEat(p)) {
                    p.displayClientMessage(new StringTextComponent("Not high enough level."), false);
                    return stack;
                }

                data.uses--;

                if (data.uses < 1) {
                    stack.shrink(1);
                } else {
                    StackSaving.CRAFTED_CONSUMABLE.saveTo(stack, data);
                }

                p.addEffect(new EffectInstance(
                    SlashPotions.CRAFTED_CONSUMABLES_MAP.get(skill)
                        .get(), data.seconds * 20, 1));

                Load.Unit(p).statusEffects.addConsumableEffect(skill, data);
            }

        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        try {

            CraftingProcessData pdata = StackSaving.CRAFT_PROCESS.loadFrom(stack);

            if (pdata != null) {
                return;
            }

            CraftedConsumableData data = StackSaving.CRAFTED_CONSUMABLE.loadFrom(stack);

            if (data != null) {
                data.makeTooltip(stack, tooltip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String GUID() {
        return "";
    }
}

