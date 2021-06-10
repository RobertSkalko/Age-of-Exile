package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.LivingEntity;

public class GiveExileStatusInRadius extends StatEffect {

    public AllyOrEnemy ally_or_enemy;
    public int seconds = 10;
    public String effect = "";
    public float radius = 4;

    public GiveExileStatusInRadius() {
        super("", "give_exile_effect_in_radius");
    }

    public GiveExileStatusInRadius(String id, AllyOrEnemy ally_or_enemy, int seconds, String effect) {
        super(id, "give_exile_effect_in_radius");
        this.ally_or_enemy = ally_or_enemy;
        this.seconds = seconds;
        this.effect = effect;
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        LivingEntity en = event.getSide(statSource);

        ExileEffect eff = Database.ExileEffects()
            .get(effect);

        EntityFinder.start(en, LivingEntity.class, en.getBlockPos())
            .finder(EntityFinder.SelectionType.RADIUS)
            .searchFor(ally_or_enemy)
            .radius(radius)
            .build()
            .forEach(x -> {

                ExilePotionEvent potionEvent = EventBuilder.ofEffect(en, x, Load.Unit(en)
                    .getLevel(), eff, GiveOrTake.give, seconds * 20)
                    .build();
                potionEvent.Activate();

            });

    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return GiveExileStatusInRadius.class;
    }
}