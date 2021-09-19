package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ScrollBuffData implements ITooltip {

    @Store
    public int lvl = 1;
    @Store
    public String id = "";
    @Store
    public String rar = IRarity.COMMON_ID;

    public ScrollBuff getBuff() {
        return ExileDB.ScrollBuffs()
            .get(id);
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rar);
    }

    public List<ExactStatData> getStats() {
        List<ExactStatData> list = new ArrayList<>();
        getBuff().stats.forEach(x -> {
            int perc = (int) (getRarity().item_tier_power * 100); // todo this is close approximation
            list.add(x.ToExactStat(perc, lvl));
        });
        return list;
    }

    public void saveToStack(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
        LoadSave.Save(this, stack.getTag(), "sb");

    }

    public boolean canUse(PlayerEntity player) {
        return Load.Unit(player)
            .getLevel() >= lvl;
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {
        TooltipInfo info = new TooltipInfo();

        TooltipUtils.addRequirements(ctx.tooltip, lvl, new StatRequirement(), ctx.data);

        getStats().forEach(x -> ctx.tooltip.addAll(x.GetTooltipString(info)));

        ctx.tooltip.add(new StringTextComponent("1 Min"));

        ctx.tooltip.add(TooltipUtils.rarity(getRarity()));
    }

}
