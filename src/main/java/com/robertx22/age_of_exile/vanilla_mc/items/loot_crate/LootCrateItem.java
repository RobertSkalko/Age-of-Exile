package com.robertx22.age_of_exile.vanilla_mc.items.loot_crate;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class LootCrateItem extends Item implements IGUID {
    public LootCrateItem() {
        super(new Properties().tab(CreativeTabs.GemRuneCurrency));
    }

    public static List<LootType> LOOT_TYPES = Arrays.asList(LootType.Gem, LootType.Rune, LootType.Currency);

    public LootCrateData getData(ItemStack stack) {
        return StackSaving.LOOT_CRATE.loadFrom(stack);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClientSide) {
            try {

                ItemStack stack = player.getItemInHand(hand);
                stack.shrink(1);

                ItemStack reward = ItemStack.EMPTY;

                LootCrateData data = StackSaving.LOOT_CRATE.loadFrom(stack);

                if (data.type == LootType.Gem) {
                    Gem gem = ExileDB.Gems()
                        .getFilterWrapped(x -> data.tier >= x.tier)
                        .random();
                    reward = new ItemStack(gem.getItem());
                } else if (data.type == LootType.Rune) {
                    Rune rune = ExileDB.Runes()
                        .getFilterWrapped(x -> data.tier >= x.tier)
                        .random();
                    reward = new ItemStack(rune.getItem());
                } else if (data.type == LootType.Currency) {
                    CurrencyItem currency = ExileDB.CurrencyItems()
                        .getFilterWrapped(x -> data.tier >= x.getTier())
                        .random();
                    reward = new ItemStack(currency);
                }

                SoundUtils.ding(player.level, player.blockPosition());
                PlayerUtils.giveItem(reward, player);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {

            for (int tier = 1; tier < GameBalanceConfig.get().MAX_LEVEL / 10; tier++) {
                for (LootType type : LOOT_TYPES) {
                    ItemStack stack = new ItemStack(this);

                    LootCrateData data = new LootCrateData();
                    data.type = type;
                    data.tier = tier;

                    StackSaving.LOOT_CRATE.saveTo(stack, data);

                    stack.getTag()
                        .putInt("CustomModelData", type.custommodeldata);

                    stacks.add(stack);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip,
                                ITooltipFlag flagIn) {

        LootCrateData data = getData(stack);

        if (data != null) {
            tooltip.add(TooltipUtils.gearTier(data.tier));
        }

    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        LootCrateData data = getData(stack);
        if (data != null) {
            return data.type.word.locName()
                .append(" ")
                .append(Words.Loot.locName())
                .append(" ")
                .append(Words.Crate.locName());
        }
        return new StringTextComponent("Box");

    }

    @Override
    public String GUID() {
        return "loot_crate/default";
    }
}
