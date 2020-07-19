package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.GemstoneSword;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.generators.util.GearCreationUtils;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.vanilla_mc.items.BaseItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;




import java.util.ArrayList;
import java.util.List;

public class ItemNewbieGearBag extends BaseItem {

    public ItemNewbieGearBag() {
        super(new Settings().group(CreativeTabs.MyModTab));

    }

    public static int ITEMS_AMOUNT = 6;

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isClient) {
            try {

                List<BaseGearType> list = new ArrayList<>();

                list.add(GemstoneSword.INSTANCE);

                list.forEach(x -> {
                    GearItemData data = getBlueprint(x).createData();

                    data.isSalvagable = false;
                    ItemStack weaponStack = GearCreationUtils.CreateStack(data);
                    playerIn.dropItem(weaponStack, false, true);
                });

                playerIn.getStackInHand(handIn)
                    .decrement(1);

                return new TypedActionResult<ItemStack>(ActionResult.PASS, playerIn.getStackInHand(handIn));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, playerIn.getStackInHand(handIn));
    }

    private static GearBlueprint getBlueprint(BaseGearType type) {
        GearBlueprint print = new GearBlueprint(1);
        print.gearItemSlot.set(type);
        print.rarity.setSpecificRarity(0);

        return print;

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<MutableText> tooltip,
                               TooltipContext flagIn) {

        tooltip.add(CLOC.tooltip("newbie_gear_bag"));

        tooltip.add(CLOC.lore("newbie_gear_bag1"));
        tooltip.add(CLOC.lore("newbie_gear_bag2"));

    }

}
