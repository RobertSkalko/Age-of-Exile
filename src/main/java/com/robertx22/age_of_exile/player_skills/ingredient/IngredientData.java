package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

@Storable
public class IngredientData {

    @Store
    public String id = "";
    @Store
    public int tier = 1;

    public static IngredientData of(SlashIngredient ing, int tier) {
        IngredientData data = new IngredientData();
        data.id = ing.GUID();
        data.tier = tier;
        return data;
    }

    public void makeTooltip(List<ITextComponent> tip) {

        SlashIngredient ingredient = getIngredient();
        tip.clear();

        int lvl = LevelUtils.tierToLevel(tier);

        tip.add(ingredient.locName());

        tip.add(new StringTextComponent(""));

        tip.add(Words.Ingredient.locName()
            .withStyle(TextFormatting.DARK_GRAY));
        tip.add(new StringTextComponent(""));

        ingredient.stats.forEach(x -> {
            tip.addAll(x.getEstimationTooltip(lvl));
        });

        tip.add(new StringTextComponent(""));

        tip.add(TooltipUtils.gearTier(tier));
        tip.add(TooltipUtils.gearRarity(ExileDB.GearRarities()
            .get(ingredient.rar)));

        tip.add(new StringTextComponent(""));

        tip.add(new StringTextComponent("Used in:"));
        ingredient.allowed_in.forEach(x -> {
            tip.add(new StringTextComponent(TooltipUtils.CHECKMARK + " ").withStyle(TextFormatting.GREEN)
                .append(ExileDB.PlayerSkills()
                    .get(x).type_enum.word.locName())
                .withStyle(TextFormatting.GRAY));
        });

    }

    public SlashIngredient getIngredient() {
        return ExileDB.Ingredients()
            .get(id);
    }
}
