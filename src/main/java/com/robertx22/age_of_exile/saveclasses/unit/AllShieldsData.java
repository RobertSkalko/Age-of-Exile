package com.robertx22.age_of_exile.saveclasses.unit;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

@Storable
public class AllShieldsData {

    @Store
    public List<ShieldData> shields = new ArrayList<>();

    public int getTotalShields() {
        return shields.stream()
            .mapToInt(x -> (int) x.amount)
            .sum();
    }

    public void giveShield(ShieldData data) {
        this.shields.add(data);
    }

    public void onTicksPassed(int ticks) {
        shields.forEach(x -> x.ticks -= ticks);
        shields.removeIf(x -> x.ticks < 0);
    }

    public int spendShieldsToReduceDamage(float dmg) {

        int reduced = 0;

        for (ShieldData x : shields) {
            if (reduced >= dmg) {
                break;
            }

            float leftToReduce = dmg - reduced;

            float spent = MathHelper.clamp(x.amount, 0, leftToReduce);
            x.amount -= spent;
            reduced += spent;

        }

        shields.removeIf(x -> x.amount < 1);

        return reduced;

    }

    @Storable
    public static class ShieldData {

        @Store
        public float amount = 0;
        @Store
        public int ticks = 0;

        public ShieldData(float amount, int ticks) {
            this.amount = amount;
            this.ticks = ticks;
        }

        public ShieldData() {
        }
    }
}

