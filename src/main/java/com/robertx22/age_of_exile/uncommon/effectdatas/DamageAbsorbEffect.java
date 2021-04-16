package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.unit.AllShieldsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;

public class DamageAbsorbEffect extends EffectData {

    @Nullable
    public Spell spell;

    public int seconds = 0;

    public DamageAbsorbEffect(float amount, int seconds, LivingEntity caster, LivingEntity target) {
        super(caster, target, Load.Unit(caster), Load.Unit(target));
        this.number = amount;
        this.seconds = seconds;
    }

    @Override
    protected void activate() {

        if (this.canceled) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();

            this.targetData.getResources()
                .shields.giveShield(new AllShieldsData.ShieldData(number, seconds * 20));

        }
    }

    public float getNumber() {
        return number;
    }
}
