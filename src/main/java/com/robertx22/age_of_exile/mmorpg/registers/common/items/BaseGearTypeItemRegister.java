package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothPantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherPantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemAxe;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemSword;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemWand;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Supplier;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<LevelRange, Item> LEATHER_BOOTS = of("armor/leather/boots/leather_boots", () -> new LeatherBootsItem("Leather Boots", false));
    public HashMap<LevelRange, Item> LEATHER_CHESTS = of("armor/leather/chest/leather_chest", () -> new LeatherChestItem("Leather Chest", false));
    public HashMap<LevelRange, Item> LEATHER_PANTS = of("armor/leather/pants/leather_pants", () -> new LeatherPantsItem("Leather Pants", false));
    public HashMap<LevelRange, Item> LEATHER_HELMETS = of("armor/leather/helmet/leather_helmet", () -> new LeatherHelmetItem("Leather Helmet", false));

    public HashMap<LevelRange, Item> CLOTH_BOOTS = of("armor/cloth/boots/cloth_boots", () -> new ClothBootsItem("Cloth Boots", false));
    public HashMap<LevelRange, Item> CLOTH_CHESTS = of("armor/cloth/chest/cloth_chest", () -> new ClothChestItem("Cloth Chest", false));
    public HashMap<LevelRange, Item> CLOTH_PANTS = of("armor/cloth/pants/cloth_pants", () -> new ClothPantsItem("Cloth Pants", false));
    public HashMap<LevelRange, Item> CLOTH_HELMETS = of("armor/cloth/helmet/cloth_helmet", () -> new ClothHelmetItem("Cloth Helmet", false));

    public HashMap<LevelRange, Item> PLATE_BOOTS = of("armor/plate/boots/plate_boots", () -> new PlateBootsItem("Plate Boots", false));
    public HashMap<LevelRange, Item> PLATE_CHESTS = of("armor/plate/chest/plate_chest", () -> new PlateChestItem("Plate Chest", false));
    public HashMap<LevelRange, Item> PLATE_PANTS = of("armor/plate/pants/plate_pants", () -> new PlatePantsItem("Plate Pants", false));
    public HashMap<LevelRange, Item> PLATE_HELMETS = of("armor/plate/helmet/plate_helmet", () -> new PlateHelmetItem("Plate Helmet", false));

    public HashMap<LevelRange, Item> SWORDS = of("weapon/sword/sword", () -> new ItemSword("Sword"));
    public HashMap<LevelRange, Item> AXES = of("weapon/axe/axe", () -> new ItemAxe("Axe"));
    public HashMap<LevelRange, Item> WANDS = of("weapon/wand/wand", () -> new ItemWand("Wand"));
    public HashMap<LevelRange, Item> SCEPTERS = of("weapon/wand/scepter", () -> new ItemWand("Scepter"));
    public HashMap<LevelRange, Item> BOWS = vanilla(Items.BOW);
    public HashMap<LevelRange, Item> CROSSBOWS = vanilla(Items.CROSSBOW);

    public HashMap<LevelRange, Item> ARMOR_SHIELDS = vanilla(Items.SHIELD);
    public HashMap<LevelRange, Item> MS_SHIELDS = vanilla(Items.SHIELD);
    public HashMap<LevelRange, Item> DODGE_SHIELDS = vanilla(Items.SHIELD);

    public HashMap<LevelRange, Item> MANA_REG_RINGS = jewelry("jewelry/ring/mana_reg_ring", () -> new ItemRing("Ring"));

    public HashMap<LevelRange, Item> FIRE_RES_RINGS = jewelry("jewelry/ring/fire_res_ring", () -> new ItemRing("Ring"));
    public HashMap<LevelRange, Item> COLD_RES_RINGS = jewelry("jewelry/ring/cold_res_ring", () -> new ItemRing("Ring"));
    public HashMap<LevelRange, Item> THUNDER_RES_RINGS = jewelry("jewelry/ring/thunder_res_ring", () -> new ItemRing("Ring"));
    public HashMap<LevelRange, Item> POISON_RES_RINGS = jewelry("jewelry/ring/poison_res_ring", () -> new ItemRing("Ring"));

    public HashMap<LevelRange, Item> ALL_RES_NECKLACES = jewelry("jewelry/necklace/all_res_necklace", () -> new ItemNecklace("Necklace"));
    public HashMap<LevelRange, Item> HP_NECKLACES = jewelry("jewelry/necklace/life_necklace", () -> new ItemNecklace("Necklace"));

    @Deprecated // this should be deleted after i can make my own bows, shields etc
    private HashMap<LevelRange, Item> vanilla(Item item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, item);
            });
        return map;
    }

    private HashMap<LevelRange, Item> of(String idprefix, Supplier<Item> item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id_suffix), item.get()));
            });
        return map;
    }

    private HashMap<LevelRange, Item> jewelry(String idprefix, Supplier<Item> item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allJewelry()
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id_suffix), item.get()));
            });
        return map;
    }

    private Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    public BaseGearTypeItemRegister() {

    }

}
