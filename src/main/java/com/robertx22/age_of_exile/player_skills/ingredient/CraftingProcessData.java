package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

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

    public void makeTooltip(List<ITextComponent> tip) {

        tip.clear();

        tip.add(new StringTextComponent("Crafted Item"));

        tip.add(new StringTextComponent(""));

        ingredients.forEach(x -> {
            x.getIngredient().stats.forEach(s -> {
                tip.addAll(s.getEstimationTooltip(LevelUtils.tierToLevel(x.tier)));
            });
        });

        tip.add(new StringTextComponent(""));
        tip.add(new StringTextComponent("Success Chance:" + getSuccessChance(ClientOnly.getPlayer()) + "%").withStyle(TextFormatting.RED, TextFormatting.BOLD));

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
                data.stats.add(stat);
            }
        }

        Item item = skill.getCraftResultItem();

        if (skill == PlayerSkillEnum.COOKING) {
            data.uses = 3;
            data.maxuses = 3;
        }
        if (skill == PlayerSkillEnum.ALCHEMY) {
            data.uses = 6;
            data.maxuses = 6;
        }

        return data;

    }
}
