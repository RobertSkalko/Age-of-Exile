package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.EleWepDmgEffect;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGenerated;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;
import com.robertx22.mine_and_slash.uncommon.wrappers.MapWrapper;

import java.util.List;

public class SpecificElementalWeaponDamage extends Stat implements IStatEffects, IGenerated<SpecificElementalWeaponDamage> {

    public static MapWrapper<WeaponTypes, SpecificElementalWeaponDamage> MAP = new MapWrapper();

    @Override
    public List<SpecificElementalWeaponDamage> generateAllPossibleStatVariations() {
        for (WeaponTypes x : WeaponTypes.getAll()) {
            SpecificElementalWeaponDamage stat = new SpecificElementalWeaponDamage(x);
            MAP.put(x, stat);
        }
        return MAP.getList();

    }

    private WeaponTypes weaponType;

    private SpecificElementalWeaponDamage(WeaponTypes type) {
        this.weaponType = type;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases elemental damage of x weapon";
    }

    @Override
    public String getIconPath() {
        return "ele_wep_dmg/" + weaponType.id;
    }

    public WeaponTypes weaponType() {
        return this.weaponType;
    }

    @Override
    public String GUID() {
        return "ele_" + this.weaponType.id + "_damage";
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
        return EleWepDmgEffect.INSTANCE;
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_wep_damage";
    }

    @Override
    public String locNameForLangFile() {
        return "Elemental " + this.weaponType()
            .name() + " Damage";
    }

    public static void register() {
        for (WeaponTypes x : WeaponTypes.getAll()) {
            SpecificElementalWeaponDamage stat = new SpecificElementalWeaponDamage(x);
            MAP.put(x, stat);
            stat.registerToSlashRegistry();
        }
    }

}
