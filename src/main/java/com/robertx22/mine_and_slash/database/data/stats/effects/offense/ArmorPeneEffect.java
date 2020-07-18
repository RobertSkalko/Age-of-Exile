package com.robertx22.mine_and_slash.database.data.stats.effects.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseAnyEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IElementalEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IPenetrable;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class ArmorPeneEffect extends BaseAnyEffect {

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public EffectData activate(EffectData effect, StatData data, Stat stat) {

        IPenetrable ipene = (IPenetrable) effect;
        ipene.SetArmorPenetration(ipene.GetArmorPenetration() + (int) data.getAverageValue());

        return effect;
    }

    @Override
    public boolean canActivate(EffectData effect, StatData data, Stat stat) {
        if (effect instanceof IElementalEffect) {
            IElementalEffect ele = (IElementalEffect) effect;
            return ele.GetElement()
                .equals(Elements.Physical);
        }

        return false;
    }

}
