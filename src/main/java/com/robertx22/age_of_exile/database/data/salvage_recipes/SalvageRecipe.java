package com.robertx22.age_of_exile.database.data.salvage_recipes;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.JsonExileRegistry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SalvageRecipe implements JsonExileRegistry<SalvageRecipe>, IAutoGson<SalvageRecipe>, IAutoLocName {

    public static SalvageRecipe SERIALIZER = new SalvageRecipe();
    public String id;

    public String loot_table_output = "";
    public int loot_table_rolls = 1;
    public transient String locname = "";

    public List<ItemIngredient> item_req = new ArrayList<>();

    public boolean matches(List<ItemStack> items) {

        List<ItemStack> test = new ArrayList<>(items);

        for (ItemIngredient x : item_req) {

            Optional<ItemStack> opt = test.stream()
                .filter(e -> x.matches(e))
                .findFirst();

            if (opt.isPresent()) {
                test.remove(opt.get());
                continue;
            } else {
                return false;
            }

        }

        return true;
    }

    public static SalvageRecipe of(String id, String locname, Identifier loottable, ItemIngredient... ingredients) {
        SalvageRecipe r = new SalvageRecipe();

        r.id = id;
        r.locname = locname;
        r.loot_table_output = loottable.toString();
        r.item_req = new ArrayList<>(Arrays.asList(ingredients));

        return r;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    public SalvageRecipe rolls(int rolls) {
        this.loot_table_rolls = rolls;
        return this;
    }

    @Override
    public ExileRegistryTypes getExileRegistryType() {
        return ExileRegistryTypes.SALVAGE_RECIPE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<SalvageRecipe> getClassForSerialization() {
        return SalvageRecipe.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return "salvage_recipe." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
