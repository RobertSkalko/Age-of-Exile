package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class CraftingProcessData {

    @Store
    public String prof = "";

    @Store
    public List<IngredientData> ingredients = new ArrayList<>();

    public int getSuccessChance(PlayerEntity player) {
        return 100 - ((ingredients.size() - 1) * 10);
    }

    public int getFailChance(PlayerEntity player) {
        return 100 - getSuccessChance(player);
    }

    public int getAverageTier() {
        return ingredients.stream()
            .mapToInt(x -> x.tier)
            .sum()
            / ingredients.size();
    }

    public float getStatMulti() {

        int total = ingredients.size();

        List<SlashIngredient> distinct = ingredients.stream()
            .map(x -> x.getIngredient())
            .distinct()
            .collect(Collectors.toList());

        float multi = 1F;

        for (SlashIngredient x : distinct) {
            int amount = (int) this.ingredients.stream()
                .filter(e -> e.getIngredient()
                    .GUID()
                    .equals(x.GUID()))
                .count();

            int morethantwo = MathHelper.clamp(amount - 2, 0, 100);
            multi -= morethantwo * 0.1F;
        }

        return multi;
    }

    public void makeTooltip(ItemStack stack, List<ITextComponent> tip) {

        tip.clear();

        IFormattableTextComponent name = new StringTextComponent("").append(stack.getDisplayName())
            .withStyle(TextFormatting.DARK_AQUA);

        tip.add(new StringTextComponent(""));

        ingredients.forEach(x -> {
            x.getIngredient().stats.forEach(s -> {
                tip.addAll(s.getEstimationTooltip(LevelUtils.tierToLevel(x.tier)));
            });
        });

        tip.add(new StringTextComponent(""));
        tip.add(new StringTextComponent("Success Chance:" + getSuccessChance(ClientOnly.getPlayer()) + "%").withStyle(TextFormatting.RED, TextFormatting.BOLD));
        tip.add(new StringTextComponent(""));
        tip.add(new StringTextComponent("Stat Multiplier:" + (int) (getStatMulti() * 100) + "%").withStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD));

    }

    public CraftedConsumableData craft(PlayerEntity player) {
        CraftedConsumableData data = new CraftedConsumableData();
        data.prof = prof;

        PlayerSkillEnum skill = ExileDB.PlayerSkills()
            .get(prof).type_enum;

        for (IngredientData x : ingredients) {
            int lvl = LevelUtils.tierToLevel(x.tier);
            int perc = RandomUtils.RandomRange(0, 100);
            for (StatModifier s : x.getIngredient().stats) {
                ExactStatData stat = s.ToExactStat(perc, lvl);
                stat.multiplyBy(skill.craftedStatMulti);
                stat.multiplyBy(getStatMulti());
                data.stats.add(stat);
            }
        }

        Item item = skill.getCraftResultItem();

        data.tier = this.getAverageTier();

        if (skill == PlayerSkillEnum.COOKING) {
            data.uses = 3;
            data.maxuses = 3;
            data.seconds = 60 * 30;
        } else if (skill == PlayerSkillEnum.ALCHEMY) {
            data.uses = 6;
            data.maxuses = 6;
            data.seconds = 60 * 10;
        } else if (skill == PlayerSkillEnum.INSCRIBING) {
            data.uses = 3;
            data.maxuses = 3;
            data.seconds = 60 * 5;
        }

        return data;

    }
}
