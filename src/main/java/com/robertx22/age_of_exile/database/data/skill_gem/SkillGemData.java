package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SkillGemData implements ITooltipList, ICommonDataItem<GearRarity> {

    @Store
    public String id = "";
    @Store
    public int lvl = 1;
    @Store
    public boolean sal = true;

    public SkillGem getSkillGem() {
        return ExileDB.SkillGems()
            .get(id);
    }

    @Override
    public String getRarityRank() {
        return IRarity.COMMON_ID;
    }

    @Override
    public Rarity getRarity() {
        return ExileDB.GearRarities()
            .get(getRarityRank());
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();
        try {
            list.add(new LiteralText(""));

            if (this.getSkillGem().type == SkillGemType.SKILL_GEM) {
                Spell spell = ExileDB.Spells()
                    .get(getSkillGem().spell_id);
                //SpellCastContext ctx = new SpellCastContext(info.player, 0, spell);
                list.addAll(spell.GetTooltipString(this, spell, info));
            }

            list.add(new LiteralText(""));

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
