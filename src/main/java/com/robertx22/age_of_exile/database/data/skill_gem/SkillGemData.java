package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SkillGemData implements ITooltipList, ICommonDataItem<SkillGemRarity> {

    @Store
    public String id = "";
    @Store
    public String rar = "";
    @Store
    public int stat_perc = 100;
    @Store
    public int lvl = 1;
    @Store
    public boolean sal = true;

    // only support gems should have random stats
    @Store
    public List<StatModifier> random_stats = new ArrayList<>();

    public void tryLevel() {

        if (lvl < GameBalanceConfig.get().MAX_LEVEL) {
            lvl++;
        }

    }

    public boolean canPlayerUse(PlayerEntity player) {

        if (getSkillGem() == null) {
            return false;
        }

        if (lvl > Load.Unit(player)
            .getLevel()) {
            return false;
        }

        if (!getSkillGem().req.meetsReq(getLevelForRequirement(player), Load.Unit(player))) {
            return false;
        }

        return true;
    }

    public int getLevelForRequirement(PlayerEntity player) {

        int level = lvl;

        if (getSkillGem().isSpell()) {
            if (getSkillGem().getSpell().config.scale_mana_cost_to_player_lvl) {

                int playerlvl = Load.Unit(player)
                    .getLevel();

                if (playerlvl > lvl) {
                    level = playerlvl;
                }
            }
        }
        return level;
    }

    public SkillGem getSkillGem() {
        return Database.SkillGems()
            .get(id);
    }

    @Override
    public String getRarityRank() {
        return rar;
    }

    public SkillGemRarity getRarity() {
        return Database.SkillGemRarities()
            .get(rar);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();
        try {
            list.add(new LiteralText(""));

            TooltipUtils.addRequirements(list, getLevelForRequirement(info.player), getSkillGem().req, info.unitdata);

            List<ExactStatData> cStats = getSkillGem().getConstantStats(this);

            if (this.getSkillGem().type == SkillGemType.SKILL_GEM) {
                Spell spell = Database.Spells()
                    .get(getSkillGem().spell_id);
                //SpellCastContext ctx = new SpellCastContext(info.player, 0, spell);
                list.addAll(spell.GetTooltipString(this, spell, info));
            }

            list.add(new LiteralText(""));

            if (!cStats.isEmpty()) {
                list.add(new LiteralText("Supported Skill Gains: "));
                cStats.forEach(x -> list.addAll(x.GetTooltipString(info)));
            }
            if (!random_stats.isEmpty()) {
                list.add(new LiteralText(""));
                list.add(new LiteralText("Random Stats: "));
                this.getSkillGem()
                    .getRandomStats(this)
                    .forEach(x -> list.addAll(x.GetTooltipString(info)));

            }

            list.add(new LiteralText(""));
            list.add(TooltipUtils.rarity(getRarity())
                .formatted(getRarity().textFormatting()));

            list.add(new LiteralText(""));
            list.add(Words.Press_Shift_For_More_Info.locName()
                .formatted(Formatting.BLUE));

            TooltipUtils.removeDoubleBlankLines(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public List<ItemStack> getSalvageResult(float salvageBonus) {
        List<ItemStack> list = new ArrayList<>();

        if (RandomUtils.roll(ModConfig.get().Server.RANDOM_SKILL_GEM_SALVAGE_CHANCE)) {
            SkillGemBlueprint blueprint = new SkillGemBlueprint(this.lvl);
            list.add(blueprint.createStack());
        } else {
            list.add(new ItemStack(Items.GUNPOWDER));
        }

        return list;
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return sal;
    }

    @Override
    public ItemstackDataSaver<SkillGemData> getStackSaver() {
        return StackSaving.SKILL_GEMS;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

    }

}
