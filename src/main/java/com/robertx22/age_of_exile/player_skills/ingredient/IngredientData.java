package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
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

    public TextComponent getStars() {
        GearRarity rar = ExileDB.GearRarities()
            .get(getIngredient().rar);

        TextFormatting BLANK = TextFormatting.GRAY;
        TextFormatting format = rar.textFormatting();

        String txt = "";
        if (rar.item_tier == 0) {
            txt = " [✫✫✫]";
        } else if (rar.item_tier == 1) {
            txt = " " + format + "[✫" + BLANK + "✫✫" + format + "]";
        } else if (rar.item_tier == 2) {
            txt = " " + format + "[✫✫" + BLANK + "✫" + format + "]";
        } else if (rar.item_tier == 3) {
            txt = " " + format + "[✫✫✫]";
        }

        return new StringTextComponent(txt);
    }

    public void makeTooltip(List<ITextComponent> tip) {
        GearRarity rar = ExileDB.GearRarities()
            .get(getIngredient().rar);

        SlashIngredient ingredient = getIngredient();
        tip.clear();

        int lvl = LevelUtils.tierToLevel(tier);

        tip.add(ingredient.locName()
            .withStyle(rar.textFormatting())
            .append(getStars()));

        //  tip.add(new StringTextComponent(""));

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
            PlayerSkill skill = ExileDB.PlayerSkills()
                .get(x);
            if (skill != null) {
                tip.add(new StringTextComponent(TooltipUtils.CHECKMARK + " ").withStyle(TextFormatting.GREEN)
                    .append(skill.type_enum.word.locName())
                    .withStyle(TextFormatting.GRAY));
            }
        });

    }

    public SlashIngredient getIngredient() {
        return ExileDB.Ingredients()
            .get(id);
    }
}
