package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class JewelrySuffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_ele_dmg_jewelry")
            .add(Elements.Fire, "Of Embers")
            .add(Elements.Water, "Of Ice")
            .add(Elements.Thunder, "Of Electricity")
            .add(Elements.Nature, "Of Venom")
            .tier(1, x -> Arrays.asList(new StatModifier(15, 20, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(11, 15, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(7, 11, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(4, x -> Arrays.asList(new StatModifier(5, 7, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(5, x -> Arrays.asList(new StatModifier(2, 5, new ElementalDamageBonus(x), ModType.FLAT)))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Jewelry))
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_philosopher")
            .Named("Of the Philosopher")
            .tier(1, new StatModifier(1, 2, Intelligence.INSTANCE, ModType.FLAT))
            .tier(2, new StatModifier(1, 2, Intelligence.INSTANCE, ModType.FLAT))
            .tier(3, new StatModifier(0.5F, 1, Intelligence.INSTANCE, ModType.FLAT))
            .Req(SlotRequirement.of(x -> !x.isWeapon() && x.family()
                .isJewelry() || x.getStatRequirements().int_req > 0))
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_titan")
            .Named("Of the Titan")
            .tier(1, new StatModifier(1, 2, Strength.INSTANCE, ModType.FLAT))
            .tier(2, new StatModifier(1, 2, Strength.INSTANCE, ModType.FLAT))
            .tier(3, new StatModifier(0.5F, 1, Strength.INSTANCE, ModType.FLAT))
            .Req(SlotRequirement.of(x -> !x.isWeapon() && x.family()
                .isJewelry() || x.getStatRequirements().str_req > 0))
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_wind")
            .Named("Of the Wind")
            .tier(1, new StatModifier(1, 2, Dexterity.INSTANCE, ModType.FLAT))
            .tier(2, new StatModifier(1, 2, Dexterity.INSTANCE, ModType.FLAT))
            .tier(3, new StatModifier(0.5F, 5, Dexterity.INSTANCE, ModType.FLAT))
            .Req(SlotRequirement.of(x -> !x.isWeapon() && x.family()
                .isJewelry() || x.getStatRequirements().dex_req > 0))
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_sky")
            .Named("Of the Sky")
            .tier(1, new StatModifier(1, 1.5F, AllAttributes.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.5F, 1, AllAttributes.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.5F, AllAttributes.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Jewelry))
            .Weight(100)
            .Suffix()
            .Build();

    }
}
