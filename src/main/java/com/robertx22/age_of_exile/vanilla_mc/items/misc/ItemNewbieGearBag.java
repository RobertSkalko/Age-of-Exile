package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
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

                list.add(SlashRegistry.GearTypes()
                    .get("wand0")); // todo, how many times do i have to hard link to specific things?

                list.forEach(x -> {
                    GearItemData data = getBlueprint(x).createData();
                    data.level = 1;
                    data.isSalvagable = false;
                    ItemStack weaponStack = GearCreationUtils.CreateStack(data);
                    PlayerUtils.giveItem(weaponStack, playerIn);

                });

                // guide book
                ItemStack book = new ItemStack(Registry.ITEM.get(new Identifier("patchouli", "guide_book")));
                CompoundTag tag = new CompoundTag();
                tag.putString("patchouli:book", "mmorpg:age_of_exile_guide");
                book.setTag(tag);
                PlayerUtils.giveItem(book, playerIn);

                ItemStack apples = new ItemStack(Items.APPLE);
                apples.setCount(3);
                PlayerUtils.giveItem(apples, playerIn);

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
