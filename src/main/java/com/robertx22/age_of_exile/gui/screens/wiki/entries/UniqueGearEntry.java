package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilters;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UniqueGearEntry extends WikiEntry {

    public UniqueGear uniq;

    public UniqueGearEntry(UniqueGear uniq) {
        this.uniq = uniq;
    }

    @Override
    public ItemStack createMainStack() {

        GearBlueprint blueprint = new GearBlueprint(RandomUtils.randomFromList(uniq.getPossibleGearTypes())
            .getLevelRange()
            .randomFromRange(), 0);
        blueprint.actionsAfterGeneration.clear();
        blueprint.unidentifiedPart.set(false);

        blueprint.rarity.set(Database.GearRarities()
            .get(uniq.uniqueRarity));
        blueprint.uniquePart.set(uniq);
        blueprint.gearItemSlot.set(blueprint.uniquePart.get()
            .getBaseGearType());

        return blueprint.createStack();

    }

    @Override
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();

        int min = uniq.getPossibleGearTypes()
            .stream()
            .min(Comparator.comparingInt(x -> x.getLevelRange()
                .getMinLevel()))
            .get()
            .getLevelRange()
            .getMinLevel();
        int max = uniq.getPossibleGearTypes()
            .stream()
            .max(Comparator.comparingInt(x -> x.getLevelRange()
                .getMaxLevel()))
            .get()
            .getLevelRange()
            .getMaxLevel();

        list.add(new LiteralText("Level Range: " + min + "-" + max));

        if (!uniq.filters.list.isEmpty()) {
            list.add(new LiteralText(""));
            uniq.filters.list.forEach(x -> list.addAll(DropFilters.get(x.type)
                .getTooltip(x)));
        }

        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(uniq.locName());
    }

    @Override
    public Formatting getFormat() {
        return Formatting.RED;
    }
}
