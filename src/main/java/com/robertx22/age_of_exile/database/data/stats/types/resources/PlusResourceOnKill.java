package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.ResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class PlusResourceOnKill extends Stat implements IStatEffects {

    public static PlusResourceOnKill HEALTH = new PlusResourceOnKill(Health.getInstance(), ResourceOnKill.HEALTH);
    public static PlusResourceOnKill MANA = new PlusResourceOnKill(Mana.getInstance(), ResourceOnKill.MANA);
    public static PlusResourceOnKill MAGIC_SHIELD = new PlusResourceOnKill(MagicShield.getInstance(), ResourceOnKill.MAGIC_SHIELD);

    Stat statRestored;
    ResourceOnKill effect;

    private PlusResourceOnKill(Stat statRestored, ResourceOnKill effect) {
        this.statRestored = statRestored;
        this.effect = effect;
        this.statGroup = StatGroup.RESTORATION;
        this.scaling = StatScaling.NORMAL;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return new StatNameRegex() {
            @Override
            public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {
                return StatNameRegex.VALUE + " " + StatNameRegex.NAME;
            }
        };
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Restore " + statRestored.locNameForLangFile() + " Every time you kill a mob";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " on Kill";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_on_kill";
    }

    @Override
    public IStatEffect getEffect() {
        return effect;
    }
}
