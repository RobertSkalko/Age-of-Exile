package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorType;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<LevelRange, Item> LEATHER_BOOTS = of("armor/leather/boots/leather_boots", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.LEATHER, "Leather Boots", EquipmentSlot.FEET, false));
    public HashMap<LevelRange, Item> LEATHER_CHESTS = of("armor/leather/chest/leather_chest", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.LEATHER, "Leather Chest", EquipmentSlot.CHEST, false));
    public HashMap<LevelRange, Item> LEATHER_PANTS = of("armor/leather/pants/leather_pants", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.LEATHER, "Leather Pants", EquipmentSlot.LEGS, false));
    public HashMap<LevelRange, Item> LEATHER_HELMETS = of("armor/leather/helmet/leather_helmet", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.LEATHER, "Leather Helmet", EquipmentSlot.HEAD, false));

    public HashMap<LevelRange, Item> CLOTH_BOOTS = of("armor/cloth/boots/cloth_boots", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.CLOTH, "Cloth Boots", EquipmentSlot.FEET, false));
    public HashMap<LevelRange, Item> CLOTH_CHESTS = of("armor/cloth/chest/cloth_chest", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.CLOTH, "Cloth Chest", EquipmentSlot.CHEST, false));
    public HashMap<LevelRange, Item> CLOTH_PANTS = of("armor/cloth/pants/cloth_pants", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.CLOTH, "Cloth Pants", EquipmentSlot.LEGS, false));
    public HashMap<LevelRange, Item> CLOTH_HELMETS = of("armor/cloth/helmet/cloth_helmet", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.CLOTH, "Cloth Helmet", EquipmentSlot.HEAD, false));

    public HashMap<LevelRange, Item> PLATE_BOOTS = of("armor/plate/boots/plate_boots", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.PLATE, "Plate Boots", EquipmentSlot.FEET, false));
    public HashMap<LevelRange, Item> PLATE_CHESTS = of("armor/plate/chest/plate_chest", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.PLATE, "Plate Chest", EquipmentSlot.CHEST, false));
    public HashMap<LevelRange, Item> PLATE_PANTS = of("armor/plate/pants/plate_pants", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.PLATE, "Plate Pants", EquipmentSlot.LEGS, false));
    public HashMap<LevelRange, Item> PLATE_HELMETS = of("armor/plate/helmet/plate_helmet", (lvl) -> new BaseArmorItem(ArmorTier.from(lvl), ArmorType.PLATE, "Plate Helmet", EquipmentSlot.HEAD, false));

    public HashMap<LevelRange, Item> SWORDS = of("weapon/sword/sword", () -> new AoeSwordWeapon(WeaponTypes.Sword));
    public HashMap<LevelRange, Item> AXES = of("weapon/axe/axe", () -> new AxeWeapon("Axe"));
    public HashMap<LevelRange, Item> SCYTHES = of("weapon/scythe/scythe", () -> new ScytheWeapon());
    public HashMap<LevelRange, Item> SPEARS = of("weapon/spear/spear", () -> new SpearWeapon());
    public HashMap<LevelRange, Item> MACES = of("weapon/mace/mace", () -> new MaceWeapon());
    public HashMap<LevelRange, Item> HAMMERS = of("weapon/hammer/hammer", () -> new HammerWeapon());
    public HashMap<LevelRange, Item> GLOVES = of("weapon/glove/glove", () -> new GloveWeapon());
    public HashMap<LevelRange, Item> DAGGERS = of("weapon/dagger/dagger", () -> new DaggerWeapon());
    public HashMap<LevelRange, Item> STAFFS = of("weapon/staff/staff", () -> new StaffWeapon());
    public HashMap<LevelRange, Item> WANDS = of("weapon/wand/wand", () -> new WandWeapon("Wand"));
    public HashMap<LevelRange, Item> SCEPTERS = of("weapon/scepter/scepter", () -> new ScepterWeapon());
    public HashMap<LevelRange, Item> BOWS = of("weapon/bow/bow", () -> new BowWeapon("Bow"));
    // public HashMap<LevelRange, Item> SHIELDS = of("weapon/shield/shield", () -> new NormalShield());

    public HashMap<LevelRange, Item> PICKAXE = vanilla(Items.DIAMOND_PICKAXE);
    public HashMap<LevelRange, Item> FISHING_RODS = vanilla(Items.FISHING_ROD);
    public HashMap<LevelRange, Item> HOES = vanilla(Items.DIAMOND_HOE);
    public HashMap<LevelRange, Item> CROSSBOWS = vanilla(Items.CROSSBOW);

    public HashMap<LevelRange, Item> ARMOR_SHIELDS = vanilla(Items.SHIELD);
    public HashMap<LevelRange, Item> MS_SHIELDS = vanilla(Items.SHIELD);
    public HashMap<LevelRange, Item> DODGE_SHIELDS = vanilla(Items.SHIELD);

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
