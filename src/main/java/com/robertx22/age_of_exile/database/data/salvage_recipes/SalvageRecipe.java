package com.robertx22.age_of_exile.database.data.salvage_recipes;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;

import java.util.ArrayList;
import java.util.List;

public class SalvageRecipe implements ISerializedRegistryEntry<SalvageRecipe>, IAutoGson<SalvageRecipe> {

    public static SalvageRecipe SERIALIZER = new SalvageRecipe();
    public String id;

    public String loot_table_output = "";

    public List<ItemIngredient> item_req = new ArrayList<>();

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SALVAGE_RECIPE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<SalvageRecipe> getClassForSerialization() {
        return SalvageRecipe.class;
    }
}
