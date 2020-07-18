package com.robertx22.mine_and_slash.database.data.stats;

public interface IUsableStat {

    /**
     * 0.75 means 75% will be maximum value
     */
    public float getMaxMulti();

    /**
     * Used to get usable value. So 5000 armor turns into 50% armor reduction
     */
    public float valueNeededToReachMaximumPercentAtLevelOne();

    public default float getUsableValue(int value, int lvl) {

        if (this instanceof Stat) {
            Stat stat = (Stat) this;

            float valNeededForMax = this.valueNeededToReachMaximumPercentAtLevelOne();

            float percOfMax = (float) value / stat.scale(valNeededForMax, lvl);

            if (percOfMax > 1) {
                percOfMax = 1;
            }

            return percOfMax * getMaxMulti();

        }
        return 0;
    }
}
