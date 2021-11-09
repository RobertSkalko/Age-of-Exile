package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class RarityEssenceItem extends Item implements IGUID, ISalvagable {

    public RarityEssenceItem() {
        super(new Properties().stacksTo(64)
            .tab(CreativeTabs.GemRuneCurrency));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {

        if (this.allowdedIn(group)) {

            for (GearRarity rar : ExileDB.GearRarities()
                .getList()) {
                if (rar.rar_ess_per_sal > 0) {
                    stacks.add(rar.getRarityEssenceStack());
                }
            }

        }
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getItemInHand(handIn);

        if (!worldIn.isClientSide) {

            try {
                GearRarity rar = GearRarity.getRarityFromEssence(stack);

                if (stack.getCount() >= 9) {
                    stack.shrink(9);
                    PlayerUtils.giveItem(rar.getRarityUpgradeStack(), player);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        player.startUsingItem(handIn);
        return ActionResult.success(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        try {

            GearRarity rar = GearRarity.getRarityFromEssence(stack);

            if (rar != null) {
                tooltip.clear();
                tooltip.add(rar.locName()
                    .withStyle(rar.textFormatting())
                    .append(" ")
                    .append(Words.Essence.locName())
                    .withStyle(rar.textFormatting()));

                tooltip.add(new StringTextComponent("Click to combine 9 into a rarity upgrade stone"));
                tooltip.add(new StringTextComponent("Can be salvaged."));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String GUID() {
        return "rarity/essence/base";
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        List<ItemStack> list = new ArrayList<>();

        try {
            GearRarity rar = GearRarity.getRarityFromEssence(stack);

            ItemStack res = new ItemStack(SlashItems.SOURCE_OF_STRENGTH.get());

            int count = (int) Math.max(1, rar.dust_per_sal.random());
            res.setCount(count);

            list.add(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return true;
    }
}
