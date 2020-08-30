package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemNewbieGearBag extends Item {

    public ItemNewbieGearBag() {
        super(new Settings().group(CreativeTabs.MyModTab));

    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isClient) {
            try {

                List<BaseGearType> list = new ArrayList<>();

                list.add(BaseGearTypes.NEWBIE_WAND);

                list.forEach(x -> {
                    GearItemData data = getBlueprint(x).createData();
                    data.level = 1;
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

}
