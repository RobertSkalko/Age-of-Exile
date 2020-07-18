package com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public abstract class BaseDivineBuffSpell extends BaseSpell {

    public BaseDivineBuffSpell(ImmutableSpellConfigs configs) {
        super(configs.setSwingArmOnCast());
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();
        c.set(SC.MANA_COST, 25, 40);
        c.set(SC.CAST_TIME_TICKS, 30, 20);
        c.set(SC.COOLDOWN_SECONDS, 360, 300);
        c.set(SC.DURATION_TICKS, 30 * 20, 60 * 20);
        c.set(SC.RADIUS, 3, 4);

        c.setMaxLevel(12);
        return c;
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.INT;
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Applies buff to all allies nearby: "));

        list.addAll(getImmutableConfigs().potionEffect()
            .GetTooltipStringWithNoExtraSpellInfo(info));

        list.add(new LiteralText("Only One Divine Buff is allowed at a time! "));

        return list;

    }

}