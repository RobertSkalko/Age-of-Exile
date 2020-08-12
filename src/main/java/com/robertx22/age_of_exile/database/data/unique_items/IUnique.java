package com.robertx22.age_of_exile.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.TagList;
import com.robertx22.age_of_exile.database.data.unique_items.bases.*;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.JsonUtils;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.datapacks.seriazables.SerializableUniqueGear;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IBaseGearType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Locale;

import static com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType.SlotTag;

public interface IUnique extends IBaseGearType, ITiered, IAutoLocName, IAutoLocDesc,
    ISerializedRegistryEntry<IUnique>, ISerializable<IUnique> {

    @Override
    default boolean isFromDatapack() {
        return true;
    }

    @Override
    default String datapackFolder() {
        return getBaseGearType().GUID() + "/";
    }

    @Override
    default JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(uniqueStats(), json, "unique_stats");

        json.addProperty("gear_type", this.getBaseGearType()
            .GUID());
        json.addProperty("item_id", this.getResourceLocForItem()
            .toString());

        return json;
    }

    @Override
    default IUnique fromJson(JsonObject json) {

        String guid = getGUIDFromJson(json);
        String name = getLangNameStringFromJson(json);
        String desc = getLangDescStringFromJson(json);
        int tier = getTierFromJson(json);
        int weight = getWeightFromJson(json);

        Identifier loc = new Identifier(json.get("item_id")
            .getAsString());

        List<StatModifier> unique = JsonUtils.getStats(json, "unique_stats");

        String slot = json.get("gear_type")
            .getAsString();

        return new SerializableUniqueGear(unique, tier, weight, guid, name, desc, slot, loc);

    }

    @Override
    public default int Weight() {
        return 1000;
    }

    List<StatModifier> uniqueStats();

    @Override
    public default int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public default Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    default String getGeneratedResourceID() {
        return getGeneratedResourceFolderPath() + GUID();
    }

    default String getGeneratedResourceFolderPath() {
        return "uniques/" + getBaseGearType().family()
            .name()
            .toLowerCase(Locale.ROOT) + "/";
    }

    @Override
    public default AutoLocGroup locNameGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public default AutoLocGroup locDescGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public default SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.UNIQUE_GEAR;
    }

    default Identifier getResourceLocForItem() {
        return new Identifier(Ref.MODID, getGeneratedResourceID());
    }

    default Item getUniqueItem() {
        return Registry.ITEM.get(getResourceLocForItem());
    }

    @Override
    default String locDescLangFileGUID() {
        return "item." + Registry.ITEM.getId(getUniqueItem())
            .toString()
            + ".desc";
    }

    @Override
    public default String getPrefix() {
        return "";
    }

    @Override
    default String locNameLangFileGUID() {
        return "item." + Registry.ITEM.getId(getUniqueItem())
            .toString();
    }

    public static Item getBaseItemForRegistration(IUnique uniq) {

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

        return null;
    }

}