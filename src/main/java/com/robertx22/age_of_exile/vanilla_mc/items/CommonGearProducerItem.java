package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CommonGearProducerItem extends AutoItem {

    public CommonGearProducerItem() {
        super(new Properties().tab(CreativeTabs.MyModTab));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (world != null && !world.isClientSide) {

            ItemStack stack = user.getItemInHand(hand);

            stack.shrink(1);

            StatSoulData soul = new StatSoulData();
            soul.setCanBeOnAnySlot();
            soul.rar = ExileDB.GearRarities()
                .getFilterWrapped(x -> x.item_tier == 0)
                .random()
                .GUID();

            soul.tier = LevelUtils.levelToTier(Load.Unit(user)
                .getLevel());
            soul.can_sal = false;

            ItemStack soulstack = soul.toStack();

            PlayerUtils.giveItem(soulstack, user);
        }

        return ActionResult.success(user.getItemInHand(hand));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new StringTextComponent("Right click to produce a common gear soul."));
    }

    @Override
    public String locNameForLangFile() {
        return "Common Gear Soul Stone";
    }

    @Override
    public String GUID() {
        return "";
    }
}
