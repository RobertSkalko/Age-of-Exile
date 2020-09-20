package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemAxe;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemSword;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemWand;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<LevelRange, Item> SWORDS = of("weapon/sword/sword", new ItemSword("Sword"));
    public HashMap<LevelRange, Item> AXES = of("weapon/axe/axe", new ItemAxe("Axe"));
    public HashMap<LevelRange, Item> WANDS = of("weapon/wand/wand", new ItemWand("Wand"));
    public HashMap<LevelRange, Item> SCEPTERS = of("weapon/wand/scepter", new ItemWand("Scepter"));
    public HashMap<LevelRange, Item> BOWS = vanilla(Items.BOW);
    public HashMap<LevelRange, Item> CROSSBOWS = vanilla(Items.CROSSBOW);
    public HashMap<LevelRange, Item> SHIELDS = vanilla(Items.SHIELD);

    public HashMap<LevelRange, Item> MANA_REG_RINGS = of("jewelry/ring/mana_reg_ring_", new ItemRing("Ring"));

    public HashMap<LevelRange, Item> FIRE_RES_RINGS = of("jewelry/ring/fire_res_ring_", new ItemRing("Ring"));
    public HashMap<LevelRange, Item> COLD_RES_RINGS = of("jewelry/ring/cold_res_ring_", new ItemRing("Ring"));
    public HashMap<LevelRange, Item> THUNDER_RES_RINGS = of("jewelry/ring/thunder_res_ring_", new ItemRing("Ring"));
    public HashMap<LevelRange, Item> POISON_RES_RINGS = of("jewelry/ring/poison_res_ring_", new ItemRing("Ring"));

    public HashMap<LevelRange, Item> ALL_RES_NECKLACES = of("jewelry/necklace/all_res_necklace_", new ItemNecklace("Necklace"));
    public HashMap<LevelRange, Item> HP_NECKLACES = of("jewelry/necklace/life_necklace_", new ItemNecklace("Necklace"));

    @Deprecated // this should be deleted after i can make my own bows, shields etc
    private HashMap<LevelRange, Item> vanilla(Item item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, item);
            });
        return map;
    }

    private HashMap<LevelRange, Item> of(String idprefix, Item item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id_suffix), item));
            });
        return map;
    }

    private HashMap<LevelRange, Item> jewelry(String idprefix, Item item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allJewelry()
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id_suffix), item));
            });
        return map;
    }

    private Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    public BaseGearTypeItemRegister() {

    }

}
