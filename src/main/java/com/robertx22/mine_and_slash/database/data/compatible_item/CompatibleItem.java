package com.robertx22.mine_and_slash.database.data.compatible_item;

import com.google.gson.JsonObject;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class CompatibleItem implements ISerializable<CompatibleItem>, ISerializedRegistryEntry<CompatibleItem> {

    public static CompatibleItem EMPTY = new CompatibleItem();

    public String item_type = "gemstone_sword";
    public String guid = "guid_for_this_entry";
    public String item_id = "item_id";

    public int min_rarity = 0;
    public int max_rarity = 2;

    public int min_level = 1;
    public int max_level = Integer.MAX_VALUE;

    public boolean only_add_stats_if_loot_drop = false;
    public boolean add_to_loot_drops = true;
    public int loot_drop_weight = 1000;
    public boolean can_be_salvaged = false;

    public float chance_to_become_unique = 0.025F;
    public String unique_id = "";

    public static CompatibleItem getDefaultAuto(Item item, BaseGearType slot) {

        CompatibleItem comp = new CompatibleItem();
        comp.item_type = slot.GUID();
        comp.item_id = Registry.ITEM.getId(item)
            .toString();
        comp.guid = slot.GUID() + ":" + comp.item_id;
        comp.add_to_loot_drops = false;

        return comp;

    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("item_type", item_type);
        json.addProperty("item_id", item_id);
        json.addProperty("id", guid);

        JsonObject rarity = new JsonObject();
        rarity.addProperty("min_rarity", min_rarity);
        rarity.addProperty("max_rarity", max_rarity);
        json.add("rarity", rarity);

        JsonObject Misc = new JsonObject();
        Misc.addProperty("only_add_stats_if_loot_drop", only_add_stats_if_loot_drop);
        Misc.addProperty("add_to_loot_drops", add_to_loot_drops);
        Misc.addProperty("loot_drop_weight", loot_drop_weight);
        Misc.addProperty("can_be_salvaged", can_be_salvaged);
        json.add("misc", Misc);

        JsonObject level = new JsonObject();
        level.addProperty("min_level", min_level);
        level.addProperty("max_level", max_level);
        json.add("level", level);

        JsonObject unique = new JsonObject();
        unique.addProperty("chance_to_become_unique", chance_to_become_unique);
        unique.addProperty("unique_id", unique_id);
        json.add("unique", unique);

        return json;
    }

    @Override
    public CompatibleItem fromJson(JsonObject json) {
        CompatibleItem obj = new CompatibleItem();

        obj.item_type = json.get("item_type")
            .getAsString();
        obj.item_id = json.get("item_id")
            .getAsString();
        obj.guid = getGUIDFromJson(json);

        JsonObject rarity = json.getAsJsonObject("rarity");
        obj.min_rarity = rarity.get("min_rarity")
            .getAsInt();
        obj.max_rarity = rarity.get("max_rarity")
            .getAsInt();

        JsonObject misc = json.getAsJsonObject("misc");
        obj.only_add_stats_if_loot_drop = misc.get("only_add_stats_if_loot_drop")
            .getAsBoolean();
        obj.add_to_loot_drops = misc.get("add_to_loot_drops")
            .getAsBoolean();
        obj.loot_drop_weight = misc.get("loot_drop_weight")
            .getAsInt();
        obj.can_be_salvaged = misc.get("can_be_salvaged")
            .getAsBoolean();

        JsonObject level = json.getAsJsonObject("level");
        obj.min_level = level.get("min_level")
            .getAsInt();
        obj.max_level = level.get("max_level")
            .getAsInt();

        JsonObject unique = json.getAsJsonObject("unique");
        obj.chance_to_become_unique = unique.get("chance_to_become_unique")
            .getAsInt();
        obj.unique_id = unique.get("unique_id")
            .getAsString();

        return obj;
    }

    @Override
    public String datapackFolder() {
        return new Identifier(item_id).getNamespace() + "/";
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.COMPATIBLE_ITEM;
    }

    @Override
    public boolean isFromDatapack() {
        return true;
    }

    public String getFileName() {
        return new Identifier(item_id).getPath();
    }

    @Override
    public String GUID() {
        return guid;
    }

    private int getLevel(int playerlevel) {
        return MathHelper.clamp(playerlevel, min_level, max_level);
    }

    public ItemStack createStack(int lvl, ItemStack stack) {

        GearBlueprint blueprint = new GearBlueprint(lvl);
        blueprint.gearItemSlot.set(this.item_type);
        blueprint.rarity.minRarity = this.min_rarity;
        blueprint.rarity.maxRarity = this.max_rarity;

        if (RandomUtils.roll(chance_to_become_unique)) {
            if (blueprint.gearItemSlot.get()
                .hasUniqueItemVersions()) {
                blueprint.isUniquePart.set(true);
                if (SlashRegistry.UniqueGears()
                    .isRegistered(this.unique_id)) {
                    blueprint.uniquePart.set(SlashRegistry.UniqueGears()
                        .get(unique_id));
                }
            }
        }

        GearItemData gear = blueprint.createData();
        gear.isSalvagable = this.can_be_salvaged;
        gear.is_not_my_mod = true;

        Gear.Save(stack, gear);

        return stack;

    }

    @Override
    public boolean isRegistryEntryValid() {

        if (!SlashRegistry.GearTypes()
            .isRegistered(this.item_type)) {
            System.out.println("Invalid gear slot: " + item_type);
            return false;
        }
        if (!unique_id.isEmpty()) {
            if (!SlashRegistry.UniqueGears()
                .isRegistered(unique_id)) {
                System.out.println("Invalid unique gear id: " + unique_id);
                return false;
            }
        }

        return true;
    }

}
