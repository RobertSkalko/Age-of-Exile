package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ScepterWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<VanillaMaterial, Item> SCEPTERS = of("weapon/scepter/",
        Arrays.asList(VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON, VanillaMaterial.WOOD),
        x -> new ScepterWeapon(x));

    public HashMap<VanillaMaterial, Item> STAFFS = of("weapon/staff/",
        Arrays.asList(VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON, VanillaMaterial.WOOD),
        x -> new StaffWeapon(x));

    public HashMap<VanillaMaterial, Item> RINGS = of("jewelry/ring/", Arrays.asList(
        VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
    ), x -> new ItemRing(x));
    public HashMap<VanillaMaterial, Item> NECKLACES = of("jewelry/necklace/", Arrays.asList(
        VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
    ), x -> new ItemNecklace(x));

    @Deprecated // this should be deleted after i can make my own bows, shields etc
    private HashMap<LevelRange, Item> vanilla(Item item) {
        HashMap<LevelRange, Item> map = new HashMap<LevelRange, Item>();
        LevelRanges.allNormal()
            .forEach(x -> {
                map.put(x, item);
            });
        return map;
    }

    private HashMap<VanillaMaterial, Item> of(String idprefix, List<VanillaMaterial> list, Function<VanillaMaterial, Item> item) {
        HashMap<VanillaMaterial, Item> map = new HashMap<VanillaMaterial, Item>();
        list
            .forEach(x -> {
                map.put(x, register(ITEM, id(idprefix + x.id), item.apply(x)));
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

    private Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    public BaseGearTypeItemRegister() {

    }

}
