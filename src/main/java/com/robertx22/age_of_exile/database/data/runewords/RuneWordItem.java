package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Ids;
import com.robertx22.age_of_exile.vanilla_mc.items.ItemDefault;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class RuneWordItem extends Item implements IShapelessRecipe, IAutoLocName {

    public RuneWordItem() {
        super(new ItemDefault());
    }

    public static String getIdPath(String id) {
        return "runewords/" + id;
    }

    public static Optional<RuneWord> getRuneword(Item item) {
        return ExileDB.RuneWords()
            .getFiltered(x -> x.item_id.equals(Ids.item(item)
                .toString()))
            .stream()
            .findAny();

    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);

        RuneWord word = ExileDB.RuneWords()
            .getSerializable()
            .stream()
            .filter(x -> x.item_id.equals(Ids.item(this)
                .toString()))
            .findAny()
            .get();

        for (RuneItem.RuneType rune : word.runes) {
            Item mat = RuneItems.get(rune);
            fac.requires(mat);
        }
        return fac.unlockedBy("player_level", trigger());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        RuneWord word = getRuneword(this).get();
        return ExileDB.UniqueGears()
            .getFromSerializables(word.uniq_id)
            .locNameForLangFile();
    }

    @Override
    public String GUID() {
        return "runeword";
    }
}
