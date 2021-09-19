package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.generators.GearSoulLootGen;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemNewbieGearBag extends Item {

    public ItemNewbieGearBag() {
        super(new Properties().tab(CreativeTabs.MyModTab));

    }

    static HashMap<String, NewbieContent> MAP = new HashMap<>();
    static NewbieContent defaultContent = new NewbieContent(Arrays.asList(() -> ModRegistry.GEAR_ITEMS.STAFFS.get(VanillaMaterial.WOOD)), Arrays.asList(GearSlots.STAFF));

    static {
    }

    static void giveNewbieItemsFor(PlayerEntity player, Perk perk) {

        NewbieContent c = defaultContent;

        if (MAP.containsKey(perk.GUID())) {
            c = MAP.get(perk.GUID());
        }

        c.give(player);

    }

    static class NewbieContent {

        public List<String> gearslots;
        public List<Supplier<Item>> items;

        public List<ItemStack> stacks = new ArrayList<>();

        public NewbieContent(List<Supplier<Item>> items, List<String> gearslots) {
            this.gearslots = gearslots;
            this.items = items;

        }

        public NewbieContent addStack(ItemStack stack) {
            this.stacks.add(stack);
            return this;
        }

        public void give(PlayerEntity player) {

            items.forEach(x -> PlayerUtils.giveItem(new ItemStack(x.get()), player));

            gearslots.forEach(x -> {
                BaseGearType gear = ExileDB.GearTypes()
                    .getFilterWrapped(e -> e.gear_slot.equals(x))
                    .random();
                GearItemData data = getBlueprint(gear).createData();
                data.lvl = 1;
                data.can_sal = false;

                GearBlueprint b = new GearBlueprint(Items.AIR, 1);
                b.level.set(1);
                b.rarity.set(ExileDB.GearRarities()
                    .get(IRarity.COMMON_ID));
                b.gearItemSlot.set(gear);

                ItemStack stack = GearSoulLootGen.createSoulBasedOnGear(b);

                EnchantedBookItem.addEnchantment(stack, new EnchantmentData(Enchantments.UNBREAKING, 3));

                PlayerUtils.giveItem(stack, player);

            });

            stacks.forEach(x -> PlayerUtils.giveItem(x, player));
        }
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isClientSide) {
            try {

                List<Perk> starts = Load.playerRPGData(playerIn).talents
                    .getAllAllocatedPerks()
                    .values()
                    .stream()
                    .filter(x -> x.is_entry)
                    .collect(Collectors.toList());

                if (true || !starts.isEmpty()) { // todo

                    defaultContent.give(playerIn);
                    // ItemNewbieGearBag.giveNewbieItemsFor(playerIn, starts.get(0));

                    if (ModList.get()
                        .isLoaded("patchouli")) {
                        /*
                        // dont give till i update it
                        // guide book
                        ItemStack book = new ItemStack(Registry.ITEM.get(new Identifier("patchouli", "guide_book")));
                        CompoundTag tag = new CompoundTag();
                        tag.putString("patchouli:book", "mmorpg:age_of_exile_guide");
                        book.setTag(tag);
                        PlayerUtils.giveItem(book, playerIn);

                         */
                    }

                    playerIn.getItemInHand(handIn)
                        .shrink(1);

                } else {
                    playerIn.displayClientMessage(new StringTextComponent("Choose your path to open this. (Press [H] and then open Talent Tree scren"), false);
                }

                return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
    }

    private static GearBlueprint getBlueprint(BaseGearType type) {
        GearBlueprint print = new GearBlueprint(Items.AIR, 1);        //TODO
        print.gearItemSlot.set(type);
        print.rarity.set(ExileDB.GearRarities()
            .get(IRarity.COMMON_ID));
        return print;
    }

}
