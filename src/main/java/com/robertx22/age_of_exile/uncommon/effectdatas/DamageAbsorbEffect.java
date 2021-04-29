package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.unit.AllShieldsData;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;

public class DamageAbsorbEffect extends EffectData {

    @Nullable
    public Spell spell;

    public int seconds = 0;

    public DamageAbsorbEffect(float amount, int seconds, LivingEntity caster, LivingEntity target) {
        super(amount, caster, target);
        this.seconds = seconds;
    }

    @Override
    protected void activate() {

        if (this.data.isCanceled()) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();

            this.targetData.getResources()
                .shields.giveShield(new AllShieldsData.ShieldData(data.getNumber(), seconds * 20));

        }
    }
}
