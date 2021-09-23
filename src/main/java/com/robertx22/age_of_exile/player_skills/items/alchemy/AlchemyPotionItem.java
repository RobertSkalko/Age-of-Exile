package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.AlchemyPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class AlchemyPotionItem extends TieredItem implements IStationRecipe {

    PotionType type;

    public AlchemyPotionItem(PotionType type, SkillItemTier tier) {
        super(tier, new Properties().tab(CreativeTabs.Professions)
            .stacksTo(16));
        this.type = type;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity player) {

        stack.shrink(1);
        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;

            for (RegObj<AlchemyPotionItem> x : AlchemyPotions.POTIONS_MAP.values()) {
                p.getCooldowns()
                    .addCooldown(x.get(), 20 * 20);
            }

            PlayerUtils.giveItem(new ItemStack(Items.GLASS_BOTTLE), (PlayerEntity) player);
        }
        if (!world.isClientSide) {

            EntityData unitdata = Load.Unit(player);

            int restore = (int) (tier.percent_healed / 100F * unitdata.getResources()
                .getMax(player, this.type.resource));

            RestoreResourceEvent event = EventBuilder.ofRestore(player, player, type.resource, RestoreType.potion, restore)
                .build();
            event.Activate();

            SoundUtils.playSound(player, SoundEvents.GENERIC_DRINK, 1, 1);
        }

        return stack;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getItemInHand(handIn);
        player.startUsingItem(handIn);
        return ActionResult.success(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new StringTextComponent("Restores " + (int) tier.percent_healed + "% " + type.word).withStyle(TextFormatting.LIGHT_PURPLE));
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + type.word + " Potion";
    }

    @Override
    public String GUID() {
        return "alchemy/potion/" + type.id + "/" + tier.tier;
    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(SlashRecipeSers.ALCHEMY.get(), this, 3);
        fac.input(type.craftItem.get());
        fac.input(Items.GLASS_BOTTLE);
        fac.input(ProfessionItems.FARMING_PRODUCE.get(tier)
            .get());
        return fac.criterion("player_level", trigger());
    }
}
