package com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.PlayerDeathStatistics;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class MagicShield extends Stat implements ILocalStat {
    public static String GUID = "magic_shield";

    private MagicShield() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.MAIN;
    }

    public static MagicShield getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Works like extra health. But regen is separate.";
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield";
    }

    private static class SingletonHolder {
        private static final MagicShield INSTANCE = new MagicShield();
    }

    public static float modifyEnviroDamage(LivingEntity en, float amount) {

        if (en instanceof PlayerEntity == false) {
            return amount;
        }

        float realdmg = HealthUtils.vanillaToReal(en, amount);
        EntityCap.UnitData data = Load.Unit(en);

        float toReduce = MathHelper.clamp(realdmg, 0, data.getResources()
            .getMagicShield());

        if (toReduce <= 0) {
            return amount;
        }

        realdmg -= toReduce;

        ResourcesData.Context ms = new ResourcesData.Context(data, en,
            ResourcesData.Type.MAGIC_SHIELD,
            toReduce,
            ResourcesData.Use.SPEND
        );
        data.getResources()
            .modify(ms);

        float vanilladmg = realdmg / HealthUtils.getCombinedMaxHealth(en) * en.getMaxHealth();

        return vanilladmg;
    }

    public static float modifyEntityDamage(DamageEffect effect, float dmg) {

        float dmgReduced = MathHelper.clamp(dmg, 0, effect.targetData.getResources()
            .getMagicShield());

        float finaldmg = dmg;

        if (dmgReduced > 0) {

            if (effect.target instanceof PlayerEntity) {
                PlayerDeathStatistics.record((PlayerEntity) effect.target, effect.element, dmgReduced);
            }

            finaldmg -= dmgReduced;

            ResourcesData.Context ctx = new ResourcesData.Context(effect.targetData, effect.target,
                ResourcesData.Type.MAGIC_SHIELD, dmgReduced,
                ResourcesData.Use.SPEND
            );

            effect.targetData.getResources()
                .modify(ctx);

        }

        return finaldmg;
    }

}
