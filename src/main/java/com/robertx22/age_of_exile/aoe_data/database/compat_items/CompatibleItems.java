package com.robertx22.age_of_exile.aoe_data.database.compat_items;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CompatibleItems {

    public enum VanillaTier {
        WOOD(false, GearRarityGroups.UP_TO_MAGICAL, LevelRanges.STARTER),
        LEATHER(false, GearRarityGroups.UP_TO_RARE, LevelRanges.STARTER),
        IRON(false, GearRarityGroups.UP_TO_RARE, LevelRanges.LOW),
        DIAMOND(true, GearRarityGroups.UP_TO_EPIC, LevelRanges.MIDDLE),
        NETHERITE(true, GearRarityGroups.UP_TO_EPIC, LevelRanges.MIDDLE);

        public boolean canSalvage;
        public String rarities;
        public LevelRange range;

        VanillaTier(boolean canSalvage, String rarities, LevelRange range) {
            this.canSalvage = canSalvage;
            this.rarities = rarities;
            this.range = range;
        }
    }

    static List<CompatibleItem> get(Item item, VanillaTier tier) {

        List<CompatibleItem> all = new ArrayList<>();

        List<BaseGearType> list = new ArrayList<>();
        for (BaseGearType type : Database.GearTypes()
            .getSerializable()) {
            if (type.getLevelRange()
                .getMiddleLevel() == tier.range.getMiddleLevel()) {
                if (BaseGearType.isGearOfThisType(type, item)) {
                    list.add(type);
                }
            }
        }

        list.forEach(x -> {

            CompatibleItem c = new CompatibleItem();
            c.can_be_salvaged = tier.canSalvage;
            c.item_type = x.GUID();

            String id = Registry.ITEM.getId(item)
                .toString();

            c.rarities = tier.rarities;

            c.guid = id + "." + c.item_type;

            c.item_id = id;

            c.chance_to_become_unique = 0;

            all.add(c);
        });

        return all;
    }

    public static List<CompatibleItem> getAllForSerialization() {
        List<CompatibleItem> items = new ArrayList<>();

        try {

            items.addAll(get(Items.LEATHER_BOOTS, VanillaTier.LEATHER));
            items.addAll(get(Items.LEATHER_CHESTPLATE, VanillaTier.LEATHER));
            items.addAll(get(Items.LEATHER_HELMET, VanillaTier.LEATHER));
            items.addAll(get(Items.LEATHER_LEGGINGS, VanillaTier.LEATHER));

            items.addAll(get(Items.IRON_BOOTS, VanillaTier.IRON));
            items.addAll(get(Items.IRON_CHESTPLATE, VanillaTier.IRON));
            items.addAll(get(Items.IRON_HELMET, VanillaTier.IRON));
            items.addAll(get(Items.IRON_LEGGINGS, VanillaTier.IRON));
            items.addAll(get(Items.IRON_SWORD, VanillaTier.IRON));
            items.addAll(get(Items.IRON_AXE, VanillaTier.IRON));

            items.addAll(get(Items.DIAMOND_BOOTS, VanillaTier.DIAMOND));
            items.addAll(get(Items.DIAMOND_CHESTPLATE, VanillaTier.DIAMOND));
            items.addAll(get(Items.DIAMOND_HELMET, VanillaTier.DIAMOND));
            items.addAll(get(Items.DIAMOND_LEGGINGS, VanillaTier.DIAMOND));
            items.addAll(get(Items.DIAMOND_SWORD, VanillaTier.DIAMOND));
            items.addAll(get(Items.DIAMOND_AXE, VanillaTier.DIAMOND));

            items.addAll(get(Items.NETHERITE_BOOTS, VanillaTier.NETHERITE));
            items.addAll(get(Items.NETHERITE_CHESTPLATE, VanillaTier.NETHERITE));
            items.addAll(get(Items.NETHERITE_HELMET, VanillaTier.NETHERITE));
            items.addAll(get(Items.NETHERITE_LEGGINGS, VanillaTier.NETHERITE));
            items.addAll(get(Items.NETHERITE_SWORD, VanillaTier.NETHERITE));
            items.addAll(get(Items.NETHERITE_AXE, VanillaTier.NETHERITE));

            items.addAll(get(Items.WOODEN_AXE, VanillaTier.WOOD));
            items.addAll(get(Items.WOODEN_SWORD, VanillaTier.WOOD));
            items.addAll(get(Items.BOW, VanillaTier.WOOD));
            items.addAll(get(Items.SHIELD, VanillaTier.WOOD));

            for (BaseGearType slot : Database.GearTypes()
                .getSerializable()) {
                Item item = slot.getItem();

                if (!Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {
                    continue;
                }

                if (Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = slot.GUID();

                    String id = Registry.ITEM.getId(item)
                        .toString();

                    c.rarities = GearRarityGroups.NON_UNIQUE_ID;

                    c.guid = id;
                    c.item_id = id;

                    items.add(c);
                }
            }
            for (UniqueGear uniq : Database.UniqueGears()
                .getSerializable()) {
                Item item = uniq.getUniqueItem();

                if (!Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {
                    continue;
                }

                if (Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = uniq.getBaseGearType()
                        .GUID();

                    c.rarities = GearRarityGroups.NON_UNIQUE_ID;

                    c.chance_to_become_unique = 100;
                    c.unique_id = uniq.GUID();

                    String id = Registry.ITEM.getId(item)
                        .toString();

                    c.guid = id;
                    c.item_id = id;

                    items.add(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
