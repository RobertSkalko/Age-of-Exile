package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class ScrollBuffItem extends AutoItem {

    public ScrollBuffItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText txt = new TranslatableText(this.getTranslationKey()).formatted(Formatting.YELLOW);

        try {
            ScrollBuffData data = getData(stack);

            if (data != null) {
                txt.append(" ")
                    .append(data.getBuff()
                        .locName())
                    .formatted(Formatting.YELLOW);
            }
        } catch (Exception e) {
        }
        return txt;

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (user instanceof ServerPlayerEntity) {

            try {

                stack.decrement(1);
                ScrollBuffData data = getData(stack);

                TeamUtils.getOnlineTeamMembersInRange((PlayerEntity) user, 50)
                    .forEach(x -> {
                        ServerPlayerEntity p = (ServerPlayerEntity) x;
                        p.addStatusEffect(new StatusEffectInstance(ModRegistry.POTIONS.SCROLL_BUFF, 20 * 60 * 3));

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

        if (world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(ModRegistry.POTIONS.SCROLL_BUFF, 20 * 60));
        }

        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
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
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        ScrollBuffData data = getData(stack);

        if (data != null) {
            data.BuildTooltip(new com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
        }

    }

}
