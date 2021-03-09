package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class SkillGemItem extends Item implements IAutoLocName, IAutoModel, ICurrencyItemEffect {

    public StatAttribute attri;
    public SkillGemType gemType;

    public SkillGemItem(StatAttribute attri, SkillGemType gemType) {
        super(new Settings().maxCount(1));
        this.attri = attri;
        this.gemType = gemType;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Skill Gem";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        try {

            SkillGemData data = SkillGemData.fromStack(stack);
            SkillGem gem = data.getSkillGem();

            tooltip.clear();
            tooltip.add(gem.locName()
                .formatted(data.getRarity()
                    .textFormatting()));

            tooltip.addAll(data.GetTooltipString(new TooltipInfo(ClientOnly.getPlayer())));

            // todo

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        if (SkillGemData.fromStack(context.stack) == null) {
            return false;
        }
        if (SkillGemData.fromStack(context.Currency) == null) {
            return false;
        }
        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        try {
            SkillGemData gem = SkillGemData.fromStack(stack);
            SkillGemData sacrifice = SkillGemData.fromStack(currency);

            gem.addExpBySacrificing(sacrifice);

            gem.saveToStack(stack);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }

    @Override
    public StationType forStation() {
        return StationType.MODIFY;
    }
}
