package com.robertx22.age_of_exile.database.data.compatible_item;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class CompatibleItem implements IByteBuf<CompatibleItem>, ISerializable<CompatibleItem>, ISerializedRegistryEntry<CompatibleItem> {

    public static CompatibleItem EMPTY = new CompatibleItem();

    public String item_type = "sword0";
    public String guid = "guid_for_this_entry";
    public String item_id = "item_id";

    public String rarities;

    public int weight = 1000;

    public boolean can_be_salvaged = false;

    public float chance_to_become_unique = 0.5F;
    public String unique_id = "";

    @Override
    public CompatibleItem getFromBuf(PacketByteBuf buf) {
        CompatibleItem c = new CompatibleItem();
        c.item_type = buf.readString(500);
        c.guid = buf.readString(500);
        c.item_id = buf.readString(500);

        c.rarities = buf.readString(500);

        c.weight = buf.readInt();

        c.can_be_salvaged = buf.readBoolean();

        c.chance_to_become_unique = buf.readFloat();
        c.unique_id = buf.readString(500);
        return c;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(item_type, 500);
        buf.writeString(guid, 500);
        buf.writeString(item_id, 500);

        buf.writeString(rarities, 500);

        buf.writeInt(weight);

        buf.writeBoolean(can_be_salvaged);

        buf.writeFloat(chance_to_become_unique);
        buf.writeString(unique_id, 500);
    }

    public static CompatibleItem getDefaultAuto(Item item, BaseGearType slot) {

        CompatibleItem comp = new CompatibleItem();
        comp.item_type = slot.GUID();
        comp.item_id = Registry.ITEM.getId(item)
            .toString();
        comp.guid = slot.GUID() + ":" + comp.item_id;

        return comp;

    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("item_type", item_type);
        json.addProperty("item_id", item_id);
        json.addProperty("id", guid);

        json.addProperty("rarities", rarities);

        JsonObject Misc = new JsonObject();
        Misc.addProperty("weight", weight);
        Misc.addProperty("can_be_salvaged", can_be_salvaged);
        json.add("misc", Misc);

        JsonObject unique = new JsonObject();
        unique.addProperty("chance_to_become_unique", chance_to_become_unique);
        unique.addProperty("unique_id", unique_id);
        json.add("unique", unique);

        return json;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public CompatibleItem fromJson(JsonObject json) {
        CompatibleItem obj = new CompatibleItem();

        obj.item_type = json.get("item_type")
            .getAsString();
        obj.item_id = json.get("item_id")
            .getAsString();
        obj.guid = getGUIDFromJson(json);

        if (json.has("rarities")) {
            obj.rarities = json.get("rarities")
                .getAsString();
        } else {

            obj.rarities = GearRarityGroups.NON_UNIQUE_ID;
        }

        if (json.has("misc")) {
            JsonObject misc = json.getAsJsonObject("misc");
            if (misc.has("weight")) {
                obj.weight = misc.get("weight")
                    .getAsInt();
            } else {
                obj.weight = 1000;
            }
            if (misc.has("can_be_salvaged")) {
                obj.can_be_salvaged = misc.get("can_be_salvaged")
                    .getAsBoolean();
            } else {
                obj.can_be_salvaged = false;
            }
        } else {
            obj.can_be_salvaged = false;
            obj.weight = 1000;
        }

        if (json.has("unique")) {
            JsonObject unique = json.getAsJsonObject("unique");
            obj.chance_to_become_unique = unique.get("chance_to_become_unique")
                .getAsFloat();
            obj.unique_id = unique.get("unique_id")
                .getAsString();
        } else {
            obj.chance_to_become_unique = 0;
            obj.unique_id = "";
        }

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
    public String getFileName() {
        return guid;
    }

    @Override
    public String GUID() {
        return guid;
    }

    public ItemStack createStack(PlayerEntity player, int lvl, ItemStack stack) {

        GearBlueprint blueprint = new GearBlueprint(lvl);

        blueprint.gearItemSlot.set(this.item_type);

        int min = blueprint.gearItemSlot.get()
            .getLevelRange()
            .getMinLevel();

        int max = blueprint.gearItemSlot.get()
            .getLevelRange()
            .getMaxLevel();

        int cap = max;

        if (lvl < cap) {
            cap = MathHelper.clamp(lvl, min, max);
        }

        blueprint.level.set(MathHelper.clamp(RandomUtils.RandomRange(min, max), min, cap));

        blueprint.rarity.possible = Database.GearRarityGroups()
            .get(this.rarities)
            .getRarities();

        if (Database.UniqueGears()
            .isRegistered(unique_id)) {

            UniqueGear uniq = Database.UniqueGears()
                .get(unique_id);
            blueprint.uniquePart.set(uniq);
            blueprint.rarity.set(Database.GearRarities()
                .get(uniq.uniqueRarity));
        }

        GearItemData gear = blueprint.createData();
        gear.can_sal = this.can_be_salvaged;

        if (!Load.favor(player)
            .getRank().can_salvage_loot) {
            gear.can_sal = false;
        }

        Gear.Save(stack, gear);

        return stack;

    }

    @Override
    public boolean isRegistryEntryValid() {

        boolean valid = true;

        if (!Database.GearTypes()
            .isRegistered(this.item_type)) {
            System.out.println("\n Invalid gear slot: " + item_type);
            valid = false;
        }
        if (!unique_id.isEmpty()) {
            if (!Database.UniqueGears()
                .isRegistered(unique_id)) {
                System.out.println("\n Invalid unique gear id: " + unique_id);
                valid = false;
            }
        }
        if (!Database.GearRarityGroups()
            .isRegistered(rarities)) {
            System.out.print("\n" + rarities + " isn't a valid gear rarity group datapack identifier.");
            valid = false;
        }

        return valid;
    }

}
