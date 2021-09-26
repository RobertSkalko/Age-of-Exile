package com.robertx22.age_of_exile.player_skills.ingredient.data;

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

    static String STAR = "\u272B";

    public TextComponent getStars() {
        GearRarity rar = ExileDB.GearRarities()
            .get(getIngredient().rar);

        TextFormatting BLANK = TextFormatting.GRAY;
        TextFormatting format = rar.textFormatting();

        String txt = "";
        if (rar.item_tier == 0) {
            txt = " [" + STAR + STAR + STAR + "]";
        } else if (rar.item_tier == 1) {
            txt = " " + format + "[" + STAR + BLANK + STAR + STAR + format + "]";
        } else if (rar.item_tier == 2) {
            txt = " " + format + "[" + STAR + STAR + BLANK + STAR + format + "]";
        } else if (rar.item_tier == 3) {
            txt = " " + format + "[" + STAR + STAR + STAR + "]";
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
            if (x.GetStat().is_long) {
                tip.add(new StringTextComponent(""));
            }
        });

        if (ingredient.isOneOfAKind()) {
            tip.add(new StringTextComponent(""));
            tip.add(new StringTextComponent("Can only have one: ").append(ingredient.getOneOfAKindId())
                .withStyle(TextFormatting.LIGHT_PURPLE));
        }

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

        TooltipUtils.removeDoubleBlankLines(tip);

    }

    public SlashIngredient getIngredient() {
        return ExileDB.Ingredients()
            .get(id);
    }
}
