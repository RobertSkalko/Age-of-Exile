package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.ClothSlippers;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.OccultistRobes;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.SilkPants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.SorcererCirclet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.HunterHood;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.LeatherLeggings;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.RawhideBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.WildTunic;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.Buckler;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.SpiritShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.TowerShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronChestplate;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronGreaves;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronLegguards;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.Crossbow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.HunterBow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.GemstoneSword;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.PrimitiveAxe;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.SageWand;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothPantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherPantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.offhands.NormalShield;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemAxe;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemBow;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemSword;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemWand;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public Item PLATE_BOOTS = of(new PlateBootsItem(0), IronGreaves.INSTANCE);
    public Item PLATE_HELMET = of(new PlateHelmetItem(0), IronHelmet.INSTANCE);
    public Item PLATE_CHEST = of(new PlateChestItem(0), IronChestplate.INSTANCE);
    public Item PLATE_PANTS = of(new PlatePantsItem(0), IronLegguards.INSTANCE);

    public Item CLOTH_SLIPPERS = of(new ClothBootsItem(0), ClothSlippers.INSTANCE);
    public Item SORCERER_CIRCLET = of(new ClothHelmetItem(0), SorcererCirclet.INSTANCE);
    public Item OCCULTIST_ROBES = of(new ClothChestItem(0), OccultistRobes.INSTANCE);
    public Item SILK_PANTS = of(new ClothPantsItem(0), SilkPants.INSTANCE);

    public Item HUNTER_HOOD = of(new LeatherHelmetItem(0), HunterHood.INSTANCE);
    public Item LEATHER_LEGGINGS = of(new LeatherPantsItem(0), LeatherLeggings.INSTANCE);
    public Item RAWHIDE_BOOTS = of(new LeatherBootsItem(0), RawhideBoots.INSTANCE);
    public Item WILD_TUNIC = of(new LeatherChestItem(0), WildTunic.INSTANCE);

    public Item GEMSTONE_SWORD = of(new ItemSword(0), GemstoneSword.INSTANCE);
    public Item PRIMITIVE_AXE = of(new ItemAxe(0), PrimitiveAxe.INSTANCE);
    public Item SAGE_WAND = of(new ItemWand(0), SageWand.INSTANCE);
    public Item BOW = of(new ItemBow(0), HunterBow.INSTANCE);
    public Item CROSSBOW = of(new CrossbowItem(ItemUtils.getDefaultGearProperties()), Crossbow.INSTANCE);
    public Item TOWER_SHIELD = of(new NormalShield(TowerShield.INSTANCE), TowerShield.INSTANCE);
    public Item SPIRIT_SHIELD = of(new NormalShield(SpiritShield.INSTANCE), SpiritShield.INSTANCE);
    public Item BUCKLER = of(new NormalShield(Buckler.INSTANCE), Buckler.INSTANCE);

    public Item HEALTH_NECKLACE = of(new ItemNecklace(0), LifeNecklace.INSTANCE);
    public Item MANA_REG_NECKLACE = of(new ItemNecklace(0), OccultNecklace.INSTANCE);
    public Item MANA_RING = of(new ItemRing(0), OccultRing.INSTANCE);
    public Item LIFE_RING = of(new ItemRing(0), LifeRing.INSTANCE);

}
