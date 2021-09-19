package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ScrollBuffItem extends AutoItem {

    public ScrollBuffItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        IFormattableTextComponent txt = new TranslationTextComponent(this.getDescriptionId()).withStyle(TextFormatting.YELLOW);

        try {
            ScrollBuffData data = getData(stack);

            if (data != null) {
                txt.append(" ")
                    .append(data.getBuff()
                        .locName())
                    .withStyle(TextFormatting.YELLOW);
            }
        } catch (Exception e) {
        }
        return txt;

    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity user) {

        if (user instanceof ServerPlayerEntity) {

            try {

                stack.shrink(1);
                ScrollBuffData data = getData(stack);

                TeamUtils.getOnlineTeamMembersInRange((PlayerEntity) user, 50)
                    .forEach(x -> {
                        ServerPlayerEntity p = (ServerPlayerEntity) x;
                        p.addEffect(new EffectInstance(ModRegistry.POTIONS.SCROLL_BUFF, 20 * 60 * 3));

                        Load.Unit(p)
                            .getStatusEffectsData().sb = data;
                        Load.Unit(p)
                            .setEquipsChanged(true);
                        Load.Unit(p)
                            .tryRecalculateStats();
                    });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (world.isClientSide) {
            user.addEffect(new EffectInstance(ModRegistry.POTIONS.SCROLL_BUFF, 20 * 60));
        }

        return stack;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return DrinkHelper.useDrink(world, user, hand);
    }

    public static ItemStack create(ScrollBuffData data) {
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.SCROLL_BUFF);
        data.saveToStack(stack);
        return stack;
    }

    public ScrollBuffData getData(ItemStack stack) {
        return LoadSave.Load(ScrollBuffData.class, new ScrollBuffData(), stack.getOrCreateTag(), "sb");
    }

    @Override
    public String locNameForLangFile() {
        return "Scroll";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        ScrollBuffData data = getData(stack);

        if (data != null) {
            data.BuildTooltip(new com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
        }

    }

}
