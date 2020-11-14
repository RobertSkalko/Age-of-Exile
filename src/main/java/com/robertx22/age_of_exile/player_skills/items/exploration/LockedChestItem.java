package com.robertx22.age_of_exile.player_skills.items.exploration;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class LockedChestItem extends Item implements IAutoLocName, IAutoModel, IReqSkillLevel {

    SkillItemTier tier;

    public LockedChestItem(SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Tinkering));
        this.tier = tier;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(tier.format);
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
        return Registry.ITEM.getId(this)
            .toString();
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {

            ItemStack stack = player.getStackInHand(hand);

            try {
                if (!IsSkillItemUsableUtil.canUseItem(player, stack, true)) {
                    return TypedActionResult.fail(stack);
                }

                ItemStack key = getKeyStack(player);

                if (key.isEmpty()) {
                    player.sendMessage(new LiteralText("Needs ").append(getKeyItem().getName()), false);
                    return TypedActionResult.fail(stack);

                }
                key.decrement(1);
                stack.decrement(1);

                int lvl = MathHelper.clamp(Load.Unit(player)
                    .getLevel(), tier.levelRange.getMinLevel(), tier.levelRange.getMaxLevel());

                List<ItemStack> loot = MasterLootGen.generateLoot(LootInfo.ofLockedChestItem(player, lvl));

                loot.forEach(x -> player.dropItem(x, false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

    public ItemStack getKeyStack(PlayerEntity player) {
        ItemStack stack = ItemStack.EMPTY;

        for (int i = 0; i < player.inventory.size(); i++) {
            ItemStack check = player.inventory.getStack(i);

            if (check.getItem() == getKeyItem()) {
                return check;
            }

        }

        return stack;
    }

    Item getKeyItem() {
        return ModRegistry.TINKERING.KEY_TIER_MAP.get(tier);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            tooltip.add(new LiteralText("Needs ").append(getKeyItem()
                .getName())
                .append("."));
            tooltip.add(new LiteralText("Click to open."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public PlayerSkillEnum getItemSkillType() {
        return PlayerSkillEnum.TINKERING;
    }

    @Override
    public float getSkillLevelMultiNeeded() {
        return tier.lvl_req;
    }
}
