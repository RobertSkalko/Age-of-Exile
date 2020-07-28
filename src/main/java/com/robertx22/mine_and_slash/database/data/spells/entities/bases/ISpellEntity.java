package com.robertx22.mine_and_slash.database.data.spells.entities.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;

public interface ISpellEntity {

    default void initSpellEntity() {
    }

    default int durationInTicks() {

        if (getSpellData() == null) {
            return 0;
        }

        return getSpellData().configs.get(SC.DURATION_TICKS)
            .intValue();
    }

    EntitySpellData getSpellData();

    void setSpellData(EntitySpellData data);

    default int getDefaultLifeInTicks() {
        return 200;
    }

    default Elements getElement() {
        return getSpellData().getSpell()
            .getElement();
    }

    default int getLifeInTicks() {
        return getSpellData().lifeInTicks;
    }

    public static class Options {

        public boolean knockback = true;
        public boolean activateEffect = true;

        public Options activatesEffect(boolean bool) {
            this.activateEffect = bool;
            return this;
        }

        public Options knockbacks(boolean bool) {
            this.knockback = bool;
            return this;
        }

    }

    default SpellDamageEffect dealSpellDamageTo(LivingEntity target) {
        return dealSpellDamageTo(target, new Options());
    }

    default SpellDamageEffect getSetupSpellDamage(LivingEntity target) {
        return dealSpellDamageTo(target, new Options().activatesEffect(false));
    }

    default SpellDamageEffect getSetupSpellDamage(LivingEntity target, Options opt) {
        return dealSpellDamageTo(target, opt.activatesEffect(false));
    }

    default SpellHealEffect healTarget(LivingEntity target) {
        return healTarget(target, new Options().activatesEffect(false));
    }

    default SpellHealEffect healTarget(LivingEntity target, Options opt) {

        EntitySpellData data = getSpellData();

        LivingEntity caster = data.getCaster(target.world);

        BaseSpell spell = data.getSpell();

        EntityCap.UnitData casterData = Load.Unit(caster);

        int num = getSpellData().configs.calc.getCalculatedValue(casterData, data.skillgem);

        SpellHealEffect heal = new SpellHealEffect(
            new ResourcesData.Context(caster, target, ResourcesData.Type.HEALTH, num, ResourcesData.Use.RESTORE,
                spell
            ));

        if (opt.activateEffect) {
            heal.Activate();
        }

        return heal;

    }

    default SpellDamageEffect dealSpellDamageTo(LivingEntity target, Options opt) {

        EntitySpellData data = getSpellData();

        LivingEntity caster = data.getCaster(target.world);

        BaseSpell spell = data.getSpell();

        EntityCap.UnitData casterData = Load.Unit(caster);

        int num = data.configs.calc
            .getCalculatedValue(casterData, data.skillgem);

        SpellDamageEffect dmg = new SpellDamageEffect(caster, target, num, casterData, Load.Unit(target),
            data.getSpell()
        );

        if (opt.knockback == false) {
            dmg.removeKnockback();
        }

        dmg.element = spell.getElement();

        if (opt.activateEffect) {
            dmg.Activate();
        }

        return dmg;

    }

}
