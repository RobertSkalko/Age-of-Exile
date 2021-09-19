package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DualWieldDamage extends Stat {

    public static String GUID = "dual_wield_dmg";

    private DualWieldDamage() {
        this.min = 0;
        this.scaling = StatScaling.NONE;
        this.statEffect = new DualWieldDamage.Effect();
    }

    public static DualWieldDamage getInstance() {
        return DualWieldDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Dual Wield Damage";
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.increaseByPercent(data.getValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            GearItemData gear = Gear.Load(effect.source.getOffhandItem());
            return gear != null && gear.GetBaseGearType()
                .isWeapon();
        }
    }

    private static class SingletonHolder {
        private static final DualWieldDamage INSTANCE = new DualWieldDamage();
    }
}



