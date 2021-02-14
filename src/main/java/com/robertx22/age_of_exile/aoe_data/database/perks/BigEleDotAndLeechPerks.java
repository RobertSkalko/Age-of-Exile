package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DamageOverTime;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ResourceLeech;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class BigEleDotAndLeechPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.bigStat("big_heart_of_ice", "Heart of Ice",
            new OptScaleExactStat(10, new ElementalSpellDamage(Elements.Water), ModType.FLAT),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Water), ModType.FLAT),
            new OptScaleExactStat(2, new ResourceLeech(new ResourceLeech.Info(Elements.Water, ResourceType.HEALTH, AttackType.SPELL)), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_heart_of_thunder", "Heart of Thunder",
            new OptScaleExactStat(10, new ElementalSpellDamage(Elements.Thunder), ModType.FLAT),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Thunder), ModType.FLAT),
            new OptScaleExactStat(2, new ResourceLeech(new ResourceLeech.Info(Elements.Thunder, ResourceType.HEALTH, AttackType.SPELL)), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_heart_of_fire", "Heart of Fire",
            new OptScaleExactStat(10, new ElementalSpellDamage(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(2, new ResourceLeech(new ResourceLeech.Info(Elements.Fire, ResourceType.HEALTH, AttackType.SPELL)), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_heart_of_nature", "Heart of Nature",
            new OptScaleExactStat(10, new ElementalSpellDamage(Elements.Nature), ModType.FLAT),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Nature), ModType.FLAT),
            new OptScaleExactStat(2, new ResourceLeech(new ResourceLeech.Info(Elements.Nature, ResourceType.HEALTH, AttackType.SPELL)), ModType.FLAT)
        );

        //

        PerkBuilder.bigStat("big_breath_of_water", "Breath of Ice",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Water), ModType.FLAT),
            new OptScaleExactStat(3, ChanceToApplyEffect.FROSTBURN, ModType.FLAT),
            new OptScaleExactStat(15, new DamageOverTime(Elements.Water), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_breath_of_fire", "Breath of Fire",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(3, ChanceToApplyEffect.BURN, ModType.FLAT),
            new OptScaleExactStat(15, new DamageOverTime(Elements.Fire), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_breath_of_thunder", "Breath of Thunder",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Thunder), ModType.FLAT),
            new OptScaleExactStat(3, ChanceToApplyEffect.SHOCK, ModType.FLAT),
            new OptScaleExactStat(15, new DamageOverTime(Elements.Thunder), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_breath_of_nature", "Breath of Poison",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Nature), ModType.FLAT),
            new OptScaleExactStat(3, ChanceToApplyEffect.POISON, ModType.FLAT),
            new OptScaleExactStat(15, new DamageOverTime(Elements.Nature), ModType.FLAT)
        );

    }
}
