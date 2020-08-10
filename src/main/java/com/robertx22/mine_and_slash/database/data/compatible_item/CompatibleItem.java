package com.robertx22.mine_and_slash.database.data.compatible_item;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CompatibleItem implements ISerializable<CompatibleItem>, ISerializedRegistryEntry<CompatibleItem> {

    public static CompatibleItem EMPTY = new CompatibleItem();

    public String item_type = "gemstone_sword";
    public String guid = "guid_for_this_entry";
    public String item_id = "item_id";

    public int min_rarity = 0;
    public int max_rarity = 2;

    public boolean add_to_loot_drops = true;
    public int loot_drop_weight = 1000;
    public boolean can_be_salvaged = false;

    public float chance_to_become_unique = 1;
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
        Misc.addProperty("add_to_loot_drops", add_to_loot_drops);
        Misc.addProperty("loot_drop_weight", loot_drop_weight);
        Misc.addProperty("can_be_salvaged", can_be_salvaged);
        json.add("misc", Misc);

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
        obj.add_to_loot_drops = misc.get("add_to_loot_drops")
            .getAsBoolean();
        obj.loot_drop_weight = misc.get("loot_drop_weight")
            .getAsInt();
        obj.can_be_salvaged = misc.get("can_be_salvaged")
            .getAsBoolean();

        JsonObject unique = json.getAsJsonObject("unique");
        obj.chance_to_become_unique = unique.get("chance_to_become_unique")
            .getAsFloat();
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

    public ItemStack createStack(int lvl, ItemStack stack) {

        GearBlueprint blueprint = new GearBlueprint(lvl);
        blueprint.gearItemSlot.set(this.item_type);
        blueprint.rarity.minRarity = this.min_rarity;
        blueprint.rarity.maxRarity = this.max_rarity;

        blueprint.isUniquePart.chance = chance_to_become_unique;

        GearItemData gear = blueprint.createData();
        gear.isSalvagable = this.can_be_salvaged;
        gear.is_not_my_mod = !Registry.ITEM.getId(stack.getItem())
            .getNamespace()
            .equals(Ref.MODID);

        if (!gear.is_not_my_mod) {
            // todo setting different itemstack doesn't work, idk how else to set the item inside
            // stack = new ItemStack(gear.getItem(), 1, Optional.of(stack.getTag()));
        }

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
