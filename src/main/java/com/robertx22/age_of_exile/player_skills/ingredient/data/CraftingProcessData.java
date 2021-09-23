package com.robertx22.age_of_exile.player_skills.ingredient.data;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.CraftStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.CraftedStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
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

        PlayerSkillEnum skill = getProfession();

        IFormattableTextComponent name = new StringTextComponent("").append(stack.getDisplayName())
            .withStyle(TextFormatting.DARK_AQUA);
        tip.add(name);

        tip.add(new StringTextComponent(""));

        ingredients.forEach(x -> {
            x.getIngredient().stats.forEach(s -> {
                tip.addAll(s.getEstimationTooltip(LevelUtils.tierToLevel(x.tier)));
            });
        });

        if (skill.isGearCraftingProf()) {

            GearRarity min = getMinRarity();
            GearRarity max = getMaxRarity();

            tip.add(new StringTextComponent(""));
            tip.add(new StringTextComponent("Mini Rarity: ").append(min.locName()
                .withStyle(min.textFormatting())));
            tip.add(new StringTextComponent("Max Rarity: ").append(max.locName()
                .withStyle(max.textFormatting())));

        }

        tip.add(new StringTextComponent(""));
        tip.add(new StringTextComponent("Success Chance:" + getSuccessChance(ClientOnly.getPlayer()) + "%").withStyle(TextFormatting.RED, TextFormatting.BOLD));
        tip.add(new StringTextComponent(""));
        tip.add(new StringTextComponent("Stat Multiplier:" + (int) (getStatMulti() * 100) + "%").withStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD));

    }

    private List<ExactStatData> getResultingStats() {
        PlayerSkillEnum skill = getProfession();

        List<ExactStatData> stats = new ArrayList<>();

        for (IngredientData x : ingredients) {
            int lvl = LevelUtils.tierToLevel(x.tier);
            int perc = RandomUtils.RandomRange(0, 100);
            for (StatModifier s : x.getIngredient().stats) {
                ExactStatData stat = s.ToExactStat(perc, lvl);
                stat.multiplyBy(skill.craftedStatMulti);
                stat.multiplyBy(getStatMulti());
                stats.add(stat);
            }
        }

        return stats;
    }

    public PlayerSkillEnum getProfession() {
        return ExileDB.PlayerSkills()
            .get(prof).type_enum;

    }

    GearRarity getMinRarity() {
        GearRarity rar = ExileDB.GearRarities()
            .get(IRarity.COMMON_ID);
        int uptimes = getIngredientsCount() / 4;

        for (int i = 0; i < uptimes; i++) {
            if (rar.hasHigherRarity()) {
                rar = rar.getHigherRarity();
            }
        }
        return rar;
    }

    GearRarity getMaxRarity() {
        GearRarity rar = ExileDB.GearRarities()
            .get(IRarity.COMMON_ID);
        int uptimes = getIngredientsCount();

        for (int i = 0; i < uptimes; i++) {
            if (rar.hasHigherRarity()) {
                rar = rar.getHigherRarity();
            }
        }
        return rar;
    }

    public int getIngredientsCount() {
        return ingredients.size();
    }

    public List<GearRarity> getPossibleRarities() {

        GearRarity min = getMinRarity();
        GearRarity max = getMaxRarity();

        List<GearRarity> possible = new ArrayList<>();

        possible.add(min);
        possible.add(max);

        while (min.hasHigherRarity() && min.item_tier < max.item_tier) {
            possible.add(min.getHigherRarity());
            min = min.getHigherRarity();
        }

        possible = possible.stream()
            .distinct()
            .collect(Collectors.toList());

        return possible;

    }

    public GearItemData craftGear(ItemStack stack, PlayerEntity player) {
        GearItemData data = new GearItemData();

        GearSlot slot = GearSlot.getSlotOf(stack.getItem());

        BaseGearType baseGearType = ExileDB.GearTypes()
            .getFilterWrapped(x -> x.gear_slot.equals(slot.GUID()))
            .random();

        GearRarity rar = RandomUtils.weightedRandom(getPossibleRarities());

        data.rarity = rar.GUID();
        int tier = ingredients.stream()
            .mapToInt(x -> x.tier)
            .sum() / ingredients.size();
        int lvl = LevelUtils.tierToLevel(tier);

        data.lvl = lvl;
        data.gear_type = baseGearType.GUID();

        data.baseStats.RerollFully(data);
        data.imp.RerollFully(data);

        List<ExactStatData> stats = getResultingStats();

        data.cr = new CraftedStatsData();

        while (stats.size() > rar.affixes.max_amount) {
            // don't allow more stats than rarity allows
            stats.remove(RandomUtils.RandomRange(0, stats.size() - 1));
        }

        stats.forEach(x -> {
            CraftStatData singleStatData = new CraftStatData();
            singleStatData.s = x;
            data.cr.stats.add(singleStatData);
        });

        return data; // todo
    }

    public CraftedConsumableData craftConsumable(PlayerEntity player) {

        PlayerSkillEnum skill = getProfession();

        List<ExactStatData> stats = getResultingStats();

        CraftedConsumableData data = new CraftedConsumableData();
        data.prof = prof;

        data.stats.addAll(stats);

        Item item = skill.getCraftResultItem();

        data.tier = this.getAverageTier();
        data.crafter = player.getGameProfile()
            .getName();

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
