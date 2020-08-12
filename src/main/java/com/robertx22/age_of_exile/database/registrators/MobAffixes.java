package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.mob_affixes.base.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ChangeDmgElementStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ISlashRegistryInit {

    public static MobAffix EMPTY = new MobAffix("empty", "empty", Formatting.AQUA);

    public static MobAffix COLD = new MobAffix("cold", "Cold", Water.format).setMods(new StatModifier(1, 1, ChangeDmgElementStat.PHYS_TO_FROST, ModType.FLAT))
        .icon(Water.format + Water.icon);
    public static MobAffix FLAMING = new MobAffix("flaming", "Flaming", Fire.format).setMods(new StatModifier(1, 1, ChangeDmgElementStat.PHYS_TO_FIRE, ModType.FLAT))
        .icon(Fire.format + Fire.icon);
    public static MobAffix LIGHTNING = new MobAffix("lightning", "Thunder", Thunder.format).setMods(new StatModifier(1, 1, ChangeDmgElementStat.PHYS_TO_THUNDER, ModType.FLAT))
        .icon(Thunder.format + Thunder.icon);
    public static MobAffix VENOMOUS = new MobAffix("venom", "Poison", Nature.format).setMods(new StatModifier(1, 1, ChangeDmgElementStat.PHYS_TO_POISON, ModType.FLAT))
        .icon(Nature.format + Nature.icon);

    public static MobAffix RUIN = new MobAffix("ruin", "Ruin", Formatting.GRAY).setMods(new StatModifier(1, 1, new WeaponDamage(Physical), ModType.FLAT));
    public static MobAffix VIGOROUS = new MobAffix("vit", "Vigorous", Formatting.RED).setMods(new StatModifier(10, 10, Health.getInstance(), ModType.FLAT));

    @Override
    public void registerAll() {

        List<MobAffix> all = new ArrayList<>();

        all.add(COLD);
        all.add(FLAMING);
        all.add(LIGHTNING);
        all.add(VENOMOUS);

        all.add(RUIN);
        all.add(VIGOROUS);

        all.forEach(x -> x.addToSerializables());

    }
}
