package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.LifeOnHit;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ISlashRegistryInit {

    public static MobAffix EMPTY = new MobAffix("empty", "empty", Formatting.AQUA);

    public static MobAffix COLD = new MobAffix("cold", "Cold", Water.format).setMods(new StatModifier(25, 25, new PhysConvertToEle(Water)), new StatModifier(1, 1, new WeaponDamage(Water)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
        .icon(Water.format + Water.icon);
    public static MobAffix FLAMING = new MobAffix("flaming", "Flaming", Fire.format).setMods(new StatModifier(25, 25, new PhysConvertToEle(Fire)), new StatModifier(1, 1, new WeaponDamage(Fire)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
        .icon(Fire.format + Fire.icon);
    public static MobAffix LIGHTNING = new MobAffix("lightning", "Thunder", Thunder.format).setMods(new StatModifier(25, 25, new PhysConvertToEle(Thunder)), new StatModifier(1, 1, new WeaponDamage(Thunder)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
        .icon(Thunder.format + Thunder.icon);
    public static MobAffix VENOMOUS = new MobAffix("venom", "Poison", Nature.format).setMods(new StatModifier(25, 25, new PhysConvertToEle(Nature)), new StatModifier(1, 1, new WeaponDamage(Nature)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
        .icon(Nature.format + Nature.icon);

    public static MobAffix RUIN = new MobAffix("ruin", "Ruin", Formatting.GRAY).setMods(new StatModifier(1, 1, new WeaponDamage(Physical)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static MobAffix VIGOROUS = new MobAffix("vit", "Vigorous", Formatting.RED).setMods(new StatModifier(10, 10, Health.getInstance()), new StatModifier(5, 5, ExtraMobDropsStat.getInstance()));
    public static MobAffix VAMPIRE = new MobAffix("vampire", "Vampire", Formatting.RED).setMods(new StatModifier(2, 2, LifeOnHit.getInstance()), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));

    public static MobAffix HAUNTED = new MobAffix("haunted", "Haunted", Formatting.GRAY).setMods(
        new StatModifier(5, 5, ChanceToApplyEffect.BURN),
        new StatModifier(5, 5, ChanceToApplyEffect.CHILL),
        new StatModifier(5, 5, ChanceToApplyEffect.POISON),
        new StatModifier(5, 5, ChanceToApplyEffect.STATIC),
        new StatModifier(15, 15, ExtraMobDropsStat.getInstance())
    );

    @Override
    public void registerAll() {

        List<MobAffix> all = new ArrayList<>();

        all.add(COLD);
        all.add(FLAMING);
        all.add(LIGHTNING);
        all.add(VENOMOUS);

        all.add(RUIN);
        all.add(VIGOROUS);
        all.add(VAMPIRE);
        all.add(HAUNTED);

        all.forEach(x -> x.addToSerializables());

    }
}
