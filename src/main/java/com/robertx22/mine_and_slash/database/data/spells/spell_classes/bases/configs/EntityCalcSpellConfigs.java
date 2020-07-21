package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import com.robertx22.mine_and_slash.saveclasses.spells.calc.SpellCalcData;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class EntityCalcSpellConfigs {

    @Store
    public SpellCalcData calc = null;

    @Store
    private HashMap<SC, Float> map = new HashMap<>();

    @Factory
    private EntityCalcSpellConfigs() {

    }

    public Float get(SC sc) {

        if (!map.containsKey(sc)) {

            try {
                throw new RuntimeException("Trying to get non existent value: " + sc.name());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        return map.get(sc);
    }

    public EntityCalcSpellConfigs(EntityCap.UnitData data, PlayerSpellCap.ISpellsCap spellsCap, IAbility ability) {

        SkillGemData skillgem = spellsCap.getCastingData()
            .getSkillGem(ability.GUID());

        PreCalcSpellConfigs pre = ability.getPreCalcConfig();

        if (pre.has(SC.BASE_VALUE)) {
            if (pre.has(SC.ATTACK_SCALE_VALUE)) {
                this.calc = SpellCalcData.scaleWithAttack(pre.get(SC.ATTACK_SCALE_VALUE)
                    .get(skillgem), pre.get(SC.BASE_VALUE)
                    .get(skillgem));

            } else {
                this.calc = SpellCalcData.base(pre.get(SC.BASE_VALUE)
                    .get(skillgem));

            }
        }

        pre.getMap()
            .entrySet()
            .forEach(x -> {
                this.map.put(x.getKey(), x.getValue()
                    .get(skillgem));
            });

    }
}
