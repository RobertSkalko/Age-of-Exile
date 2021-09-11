package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.DataGenKey;

import java.util.Arrays;

public class CasterHasStatCondition extends EffectCondition {

    public CasterHasStatCondition() {
        super(Arrays.asList(MapField.SPELL_MODIFIER));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {

        MarkerStat mod = (MarkerStat) ExileDB.Stats()
            .get(data.get(MapField.SPELL_MODIFIER));

        return Load.Unit(ctx.caster)
            .getUnit()
            .getCalculatedStat(mod)
            .isNotZero();

    }

    public MapHolder create(DataGenKey<MarkerStat> mod) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.SPELL_MODIFIER, mod.GUID());
        return d;
    }

    @Override
    public String GUID() {
        return "caster_has_stat";
    }

}