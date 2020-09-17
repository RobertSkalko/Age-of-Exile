package com.robertx22.age_of_exile.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.unique_items.bases.*;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.JsonUtils;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IBaseGearType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class UniqueGear implements IByteBuf<UniqueGear>, IBaseGearType, ITiered, IAutoLocName, IAutoLocDesc,
    ISerializedRegistryEntry<UniqueGear>, ISerializable<UniqueGear> {

    public static UniqueGear SERIALIZER = new UniqueGear();

    List<StatModifier> uniqueStats = new ArrayList<>();
    int tier;
    int weight = 1000;
    String guid;
    String gearType;
    Identifier itemID;

    @Override
    public UniqueGear getFromBuf(PacketByteBuf buf) {
        UniqueGear uniq = new UniqueGear();

        int amount = buf.readInt();
        for (int i = 0; i < amount; i++) {
            uniq.uniqueStats.add(StatModifier.EMPTY.getFromBuf(buf));
        }
        uniq.guid = buf.readString(50);
        uniq.gearType = buf.readString(50);
        uniq.itemID = buf.readIdentifier();

        return uniq;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeInt(this.uniqueStats.size());
        uniqueStats.forEach(x -> x.toBuf(buf));
        buf.writeString(guid, 50);
        buf.writeString(gearType, 50);
        buf.writeIdentifier(itemID);
    }

    transient String langName;
    transient String langDesc;
    transient BaseGearType serBaseGearType;

    @Override
    public String datapackFolder() {
        return getBaseGearType().family()
            .name()
            .toLowerCase(Locale.ROOT) + "/";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(uniqueStats(), json, "unique_stats");

        json.addProperty("gear_type", this.getBaseGearType()
            .GUID());
        json.addProperty("item_id", this.getResourceLocForItem()
            .toString());

        return json;
    }

    @Override
    public UniqueGear fromJson(JsonObject json) {

        UniqueGear uniq = new UniqueGear();

        uniq.guid = getGUIDFromJson(json);
        uniq.tier = getTierFromJson(json);
        uniq.weight = getWeightFromJson(json);

        uniq.itemID = new Identifier(json.get("item_id")
            .getAsString());

        uniq.uniqueStats = JsonUtils.getStats(json, "unique_stats");

        uniq.gearType = json.get("gear_type")
            .getAsString();

        return uniq;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    public String getGeneratedResourceID() {
        return getGeneratedResourceFolderPath() + GUID();
    }

    public String getGeneratedResourceFolderPath() {
        return "uniques/" + this.getBaseGearType()
            .family()
            .name()
            .toLowerCase(Locale.ROOT) + "/";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.UNIQUE_GEAR;
    }

    public Identifier getResourceLocForItemForSerialization() {
        return new Identifier(Ref.MODID, getGeneratedResourceID());
    }

    public Item getUniqueItemForSer() {
        return Registry.ITEM.get(getResourceLocForItem());
    }

    public static Item getBaseItemForRegistration(UniqueGear uniq) {

        TagList tags = uniq.getBaseGearType()
            .getTags();

        if (tags.contains(SlotTag.sword)) {
            return new BaseUniqueSword(uniq.locNameForLangFile());
        }
        if (tags.contains(SlotTag.axe)) {
            return new BaseUniqueAxe(uniq.locNameForLangFile());
        }
        if (tags.contains(SlotTag.wand)) {
            return new BaseUniqueWand(uniq.locNameForLangFile());
        }
        if (tags.contains(SlotTag.boots)) {
            return new BaseUniqueBoots(uniq.locNameForLangFile(), true);
        }
        if (tags.contains(SlotTag.chest)) {
            return new BaseUniqueChest(uniq.locNameForLangFile(), true);
        }
        if (tags.contains(SlotTag.pants)) {
            return new BaseUniquePantsItem(uniq.locNameForLangFile(), true);
        }
        if (tags.contains(SlotTag.helmet)) {
            return new BaseUniqueHelmet(uniq.locNameForLangFile(), true);
        }
        if (tags.contains(SlotTag.crossbow)) {
            return Items.CROSSBOW;
        }
        if (tags.contains(SlotTag.bow)) {
            return new BaseUniqueBow(uniq.locNameForLangFile());
        }
        if (tags.contains(SlotTag.necklace)) {
            return new BaseUniqueNecklace(uniq.locNameForLangFile());
        }
        if (tags.contains(SlotTag.ring)) {
            return new BaseUniqueRing(uniq.locNameForLangFile());
        }

        System.out.println("No item found for unique gear registration: " + uniq.GUID());

        return null;
    }

    public Identifier getResourceLocForItem() {
        return itemID;
    }

    public Item getUniqueItem() {
        return Registry.ITEM.get(itemID);
    }

    @Override
    public int getTier() {
        return tier;
    }

    public List<StatModifier> uniqueStats() {
        return this.uniqueStats;
    }

    @Override
    public String locDescForLangFile() {
        return this.langDesc;
    }

    @Override
    public String locNameForLangFile() {
        return this.langName;
    }

    @Override
    public String locDescLangFileGUID() {
        return "item." + this.itemID + ".desc";
    }

    @Override
    public String locNameLangFileGUID() {
        return "item." + this.itemID;
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public BaseGearType getBaseGearType() {
        if (!SlashRegistry.GearTypes()
            .isRegistered(this.gearType)) {
            assert this.serBaseGearType != null;
            return serBaseGearType;
        }
        return SlashRegistry.GearTypes()
            .get(gearType);
    }

}