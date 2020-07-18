package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.WeaponDamageEffect;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGenerated;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

import java.util.ArrayList;
import java.util.List;

public class SpecificWeaponDamage extends Stat implements IStatEffects, IGenerated<SpecificWeaponDamage> {

    private WeaponTypes weaponType;

    public SpecificWeaponDamage(WeaponTypes type) {
        this.weaponType = type;

    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage done if it was caused by that weapon";
    }

    public WeaponTypes weaponType() {
        return this.weaponType;
    }

    @Override
    public String getIconPath() {
        return "wep_dmg/" + weaponType.id;
    }

    @Override
    public String GUID() {
        return this.weaponType.id + "_damage";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public IStatEffect getEffect() {
        return new WeaponDamageEffect();
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "weapon_damage";
    }

    @Override
    public String locNameForLangFile() {
        return this.weaponType()
            .name() + " Damage";
    }

    @Override
    public List<SpecificWeaponDamage> generateAllPossibleStatVariations() {
        List<SpecificWeaponDamage> list = new ArrayList<>();
        WeaponTypes.getAll()
            .forEach(x -> list.add(new SpecificWeaponDamage(x)));
        return list;

    }
}
