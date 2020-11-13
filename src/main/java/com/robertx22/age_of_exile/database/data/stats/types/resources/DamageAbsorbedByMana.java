package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.math.MathHelper;

public class DamageAbsorbedByMana extends Stat {
    public static String GUID = "mana_shield";

    private DamageAbsorbedByMana() {
        this.min_val = 0;
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.MAIN;

        this.add$To$toTooltip = false;
    }

    public static DamageAbsorbedByMana getInstance() {
        return DamageAbsorbedByMana.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of damage is absorbed by mana. This effect only works if mana is not less than 50 percent capacity. Mana shield is applied after magic shield.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Of Damage Absorbed By Mana";
    }

    public static float modifyEntityDamage(DamageEffect effect, float dmg) {

        StatData data = effect.targetData.getUnit()
            .getCalculatedStat(DamageAbsorbedByMana.getInstance());

        if (data.getAverageValue() <= 0) {
            return dmg;
        }

        float currentMana = effect.targetData.getResources()
            .getMana();

        if (currentMana / effect.targetData.getUnit()
            .manaData()
            .getAverageValue() > 0.5F) {

            float maxMana = effect.targetData.getUnit()
                .manaData()
                .getAverageValue();

            float dmgReduced = MathHelper.clamp(dmg * data.getAverageValue() / 100F, 0, currentMana - (maxMana * 0.5F));

            if (dmgReduced > 0) {

                ResourcesData.Context ctx = new ResourcesData.Context(effect.targetData, effect.target,
                    ResourcesData.Type.MANA, dmgReduced,
                    ResourcesData.Use.SPEND
                );

                effect.targetData.getResources()
                    .modify(ctx);

                return dmg - dmgReduced;

            }

        }
        return dmg;
    }

    private static class SingletonHolder {
        private static final DamageAbsorbedByMana INSTANCE = new DamageAbsorbedByMana();
    }
}
