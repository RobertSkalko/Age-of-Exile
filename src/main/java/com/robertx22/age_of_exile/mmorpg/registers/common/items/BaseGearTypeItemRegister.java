package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.BowWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ScepterWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<LevelRange, Item> STAFFS = of("weapon/staff/staff", () -> new StaffWeapon());
    public HashMap<LevelRange, Item> SCEPTERS = of("weapon/scepter/scepter", () -> new ScepterWeapon());
    public HashMap<LevelRange, Item> BOWS = of("weapon/bow/bow", () -> new BowWeapon("Bow"));

    public HashMap<LevelRange, Item> PICKAXE = vanilla(Items.DIAMOND_PICKAXE);
    public HashMap<LevelRange, Item> FISHING_RODS = vanilla(Items.FISHING_ROD);
    public HashMap<LevelRange, Item> HOES = vanilla(Items.DIAMOND_HOE);
    public HashMap<LevelRange, Item> CROSSBOWS = vanilla(Items.CROSSBOW);

    public HashMap<LevelRange, Item> MANA_REG_RINGS = jewelry("jewelry/ring/mana_reg_ring", () -> new ItemRing("Ring"));
    public HashMap<LevelRange, Item> HP_RINGS = jewelry("jewelry/ring/hp_rng", () -> new ItemRing("Ring"));

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

    private HashMap<LevelRange, Item> of(String idprefix, Function<LevelRange, Item> item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id_suffix), item.apply(x)));
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
