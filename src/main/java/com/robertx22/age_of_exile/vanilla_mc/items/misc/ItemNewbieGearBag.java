package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.database.perks.StartPerks;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.DexSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.IntSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.StrSpells;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemNewbieGearBag extends Item {

    public ItemNewbieGearBag() {
        super(new Settings().group(CreativeTabs.MyModTab));

    }

    static HashMap<String, NewbieContent> MAP = new HashMap<>();
    static NewbieContent defaultContent = new NewbieContent(Arrays.asList("wand0"), Arrays.asList(IntSpells.FIREBALL_ID));

    static {

        MAP.put(StartPerks.MAGE, new NewbieContent(Arrays.asList("wand0"), Arrays.asList(IntSpells.FIREBALL_ID)));
        MAP.put(StartPerks.BATTLE_MAGE, new NewbieContent(Arrays.asList("wand0"), Arrays.asList(IntSpells.POISONBALL_ID)));
        MAP.put(StartPerks.HUNTER, new NewbieContent(Arrays.asList("bow0"), Arrays.asList(DexSpells.MULTI_SHOT_ID, DexSpells.MAKE_ARROWS)).addStack(new ItemStack(Items.ARROW, 64)));
        MAP.put(StartPerks.WARRIOR, new NewbieContent(Arrays.asList("sword0"), Arrays.asList(StrSpells.FLAME_STRIKE_ID)));
        MAP.put(StartPerks.DUELIST, new NewbieContent(Arrays.asList("sword0"), Arrays.asList(StrSpells.FLAME_STRIKE_ID)));

    }

    static void giveNewbieItemsFor(PlayerEntity player, Perk perk) {

        NewbieContent c = defaultContent;

        if (MAP.containsKey(perk.GUID())) {
            c = MAP.get(perk.GUID());
        }

        c.give(player);

    }

    static class NewbieContent {

        public List<String> gears;
        public List<String> skillgems;

        public List<ItemStack> stacks = new ArrayList<>();

        public NewbieContent(List<String> gears, List<String> skillgems) {
            this.gears = gears;
            this.skillgems = skillgems;
        }

        public NewbieContent addStack(ItemStack stack) {
            this.stacks.add(stack);
            return this;
        }

        public void give(PlayerEntity player) {

            gears.forEach(x -> {
                BaseGearType gear = Database.GearTypes()
                    .get(x);
                GearItemData data = getBlueprint(gear).createData();
                data.lvl = 1;
                data.can_sal = false;
                ItemStack stack = GearCreationUtils.CreateStack(data);

                EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(Enchantments.UNBREAKING, 1));

                PlayerUtils.giveItem(stack, player);

            });

            skillgems.forEach(x -> {
                SkillGem gem = Database.SkillGems()
                    .get(x);
                SkillGemBlueprint blueprint = new SkillGemBlueprint(1);

                blueprint.type.set(gem);
                blueprint.level.set(1);

                ItemStack stack = blueprint.createStack();

                SkillGemData data = SkillGemData.fromStack(stack);
                data.sal = false;
                data.saveToStack(stack);

                PlayerUtils.giveItem(stack, player);
            });

            stacks.forEach(x -> PlayerUtils.giveItem(x, player));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isClient) {
            try {

                List<Perk> starts = Load.perks(playerIn)
                    .getAllAllocatedPerks()
                    .values()
                    .stream()
                    .filter(x -> x.is_entry)
                    .collect(Collectors.toList());

                if (!starts.isEmpty()) {

                    ItemNewbieGearBag.giveNewbieItemsFor(playerIn, starts.get(0));
                    // guide book
                    ItemStack book = new ItemStack(Registry.ITEM.get(new Identifier("patchouli", "guide_book")));
                    CompoundTag tag = new CompoundTag();
                    tag.putString("patchouli:book", "mmorpg:age_of_exile_guide");
                    book.setTag(tag);
                    PlayerUtils.giveItem(book, playerIn);

                    playerIn.getStackInHand(handIn)
                        .decrement(1);

                } else {
                    playerIn.sendMessage(new LiteralText("Choose your path to open this. (Press [H] and then open Talent Tree scren"), false);
                }

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
        print.rarity.set(Database.GearRarities()
            .get(IRarity.COMMON_ID));
        return print;
    }

}
