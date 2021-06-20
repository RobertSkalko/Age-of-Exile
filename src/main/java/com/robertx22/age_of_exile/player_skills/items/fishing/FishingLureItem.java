package com.robertx22.age_of_exile.player_skills.items.fishing;

import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class FishingLureItem extends TieredItem {

    public LureType lureType;

    public FishingLureItem(SkillItemTier tier, LureType lureType) {
        super(tier);
        this.lureType = lureType;
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + lureType.name + " Lure";
    }

    @Override
    public String GUID() {
        return "lure/" + lureType.id + "/" + tier.tier;
    }

    public float getChanceMulti() {
        return 1F + (tier.getDisplayTierNumber() * 0.1F);
    }

    public static void spendLure(PlayerEntity player) {
        ItemStack rod = player.getMainHandStack();

        if (!(rod.getItem() instanceof FishingRodItem)) {
            return;
        }
        if (!rod.hasTag()) {
            return;
        }

        int uses = rod.getTag()
            .getInt("lure_uses");
        if (uses < 1) {
            return;
        }

        rod.getTag()
            .putInt("lure_uses", uses - 1);
    }

    public static FishingLureItem getCurrentLure(PlayerEntity player) {

        ItemStack rod = player.getMainHandStack();

        if (!(rod.getItem() instanceof FishingRodItem)) {
            return null;
        }
        if (!rod.hasTag()) {
            return null;
        }

        if (rod.getTag()
            .getInt("lure_uses") < 1) {
            return null;
        }

        String lure = rod.getTag()
            .getString("lure_id");

        Item item = Registry.ITEM.get(new Identifier(lure));

        if (item instanceof FishingLureItem) {
            return (FishingLureItem) item;
        }
        return null;

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        float multi = getChanceMulti() - 1F;
        int percent = (int) (multi * 100);

        tooltip.add(new LiteralText("Increases chance of " + lureType.name + " by " + percent + "%"));
        tooltip.add(new LiteralText("Click to apply to fishing rod"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getStackInHand(handIn);
        player.setCurrentHand(handIn);

        for (int i = 0; i < player.inventory.size(); i++) {
            ItemStack rod = player.inventory.getStack(i);
            if (rod.getItem() instanceof FishingRodItem) {
                stack.decrement(1);
                if (!rod.hasTag()) {
                    rod.setTag(new NbtCompound());
                }
                rod.getTag()
                    .putString("lure_id", Registry.ITEM.getId(stack.getItem())
                        .toString());
                rod.getTag()
                    .putInt("lure_uses", 10);
                return TypedActionResult.success(stack);
            }
        }

        player.sendMessage(new LiteralText("No fishing rod found"), false);
        return TypedActionResult.fail(stack);
    }
}
