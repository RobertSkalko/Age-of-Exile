package com.robertx22.age_of_exile.database.data.stats;

public interface IUsableStat {

    /**
     * 0.75 means 75% will be maximum value
     */
    public float getMaxMulti();

    /**
     * Used to get usable value. So 5000 armor turns into 50% armor reduction
     */
    public float valueNeededToReachMaximumPercentAtLevelOne();

    default void logUsableAmountTests() {

        Stat stat = (Stat) this;

        int val = 50;

        for (int i = 0; i < 50; i++) {

            float multi = getUsableValue(val, 1);

            System.out.print("\n For " + val + " " + stat.GUID() + " usable value is: " + multi);

            val += 50;
        }

    }

    public default float getUsableValue(int value, int lvl) {

        if (this instanceof Stat) {
            Stat stat = (Stat) this;

            float valNeededForMax = stat.scale(valueNeededToReachMaximumPercentAtLevelOne(), lvl);

            float percOfMax = (float) value / valNeededForMax / (value / valNeededForMax + 1.5F);

            if (percOfMax > 1) {
                percOfMax = 1;
            }

            return percOfMax * getMaxMulti();

        }
        return 0;
    }
}
