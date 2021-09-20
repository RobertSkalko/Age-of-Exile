package com.robertx22.age_of_exile.player_skills.items.exploration;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class LockedChestItem extends TieredItem {

    public LockedChestItem(SkillItemTier tier) {
        super(tier);
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Locked Chest";
    }

    @Override
    public String GUID() {
        return "locked_chest/" + tier.tier;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClientSide) {

            ItemStack stack = player.getItemInHand(hand);

            try {
                if (!IsSkillItemUsableUtil.canUseItem(player, stack, true)) {
                    return ActionResult.fail(stack);
                }

                ItemStack key = getKeyStack(player);

                if (key.isEmpty()) {
                    player.displayClientMessage(new StringTextComponent("Needs ").append(new TranslationTextComponent(getKeyItem().getDescriptionId())), false);
                    return ActionResult.fail(stack);

                }
                key.shrink(1);
                stack.shrink(1);

                int lvl = MathHelper.clamp(Load.Unit(player)
                    .getLevel(), tier.levelRange.getMinLevel(), tier.levelRange.getMaxLevel());

                List<ItemStack> loot = MasterLootGen.generateLoot(LootInfo.ofLockedChestItem(player, lvl));

                SoundUtils.ding(player.level, player.blockPosition());

                loot.forEach(x -> {
                    PlayerUtils.giveItem(x, player);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    public ItemStack getKeyStack(PlayerEntity player) {
        ItemStack stack = ItemStack.EMPTY;

        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack check = player.inventory.getItem(i);

            if (check.getItem() == getKeyItem()) {
                return check;
            }

        }

        return stack;
    }

    Item getKeyItem() {
        return ModRegistry.TIERED.KEY_TIER_MAP.get(tier);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

            tooltip.add(new StringTextComponent("Needs ").append(getKeyItem()
                    .getDescription())
                .append("."));
            tooltip.add(new StringTextComponent("Click to open."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
