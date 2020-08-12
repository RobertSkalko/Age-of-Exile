package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.level_based_numbers.LevelBased;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// this class should be easy to serialize as a config
// synergies add to these values.
public class PreCalcSpellConfigs {

    public PreCalcSpellConfigs() {
        set(SC.TIMES_TO_CAST, 1, 1);
        set(SC.COOLDOWN_TICKS, 0, 0);
        set(SC.COOLDOWN_SECONDS, 0, 0);
    }

    private PreCalcSpellConfigs(boolean nothing) {

    }

    public static PreCalcSpellConfigs getEmptyForSynergies() {
        return new PreCalcSpellConfigs(false);
    }

    private HashMap<SC, LevelBased> map = new HashMap<>();

    public int maxSpellLevel = 12;

    public SpellCalcData getCalc(SkillGemData skillgem) {

        if (has(SC.ATTACK_SCALE_VALUE)) {
            return SpellCalcData.scaleWithAttack(
                get(SC.ATTACK_SCALE_VALUE).get(skillgem),
                get(SC.BASE_VALUE).get(skillgem)
            );
        } else {
            return SpellCalcData.base(
                get(SC.BASE_VALUE).get(skillgem)
            );
        }
    }

    public boolean has(SC sc) {
        return map.containsKey(sc);
    }

    public HashMap<SC, LevelBased> getMap() {
        return map;
    }

    public void set(SC sc, float min, float max) {

        map.put(sc, new LevelBased(min, max).min(sc.min));
    }

    public void setDurationInSeconds(int s1, int s2) {
        set(SC.DURATION_TICKS, s1 * 20, s2 * 20);
    }

    public void multiplyValueBy(SC sc, float multi) {
        get(sc).multiplyBy(multi);
    }

    public void setMaxLevel(int lvl) {
        this.maxSpellLevel = lvl;
    }

    public LevelBased getOrEmpty(SC sc) {
        if (has(sc)) {
            return get(sc);
        }
        return LevelBased.empty();
    }

    public LevelBased get(SC sc) {

        if (!map.containsKey(sc)) {

            try {
                throw new Exception("Trying to get non existent value: " + sc.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return LevelBased.empty();
        }

        LevelBased thing = map.get(sc);

        if (thing.isEmpty()) {
            try {
                throw new Exception("Getting empty value: " + sc.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return LevelBased.empty();
        }

        return thing;

    }

    public void modifyByUserStats(SpellCastContext ctx) {
        new SpellStatsCalcEffect(ctx, this, ctx.caster, ctx.caster).Activate();
    }

    public List<MutableText> GetTooltipString(TooltipInfo info, SpellCastContext ctx) {

        List<MutableText> list = new ArrayList<>();

        map.entrySet()
            .forEach(x -> {
                if (x.getKey()
                    .shouldAddToTooltip()) {
                    String val = NumberUtils.formatForTooltip(x.getValue()
                        .get(ctx.skillGem));
                    list.add(new SText(Formatting.GRAY + "").append(x.getKey().word.locName())
                        .append(": " + Formatting.GREEN + val));

                }
            });

        return list;

    }
}
