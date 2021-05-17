package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;

public class TestSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        SpellBuilder.of("test_command", SpellConfiguration.Builder.instant(7, 15)
                .setSwingArm(), "Test command",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weight(0)
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.justAction(SpellAction.CASTER_USE_COMMAND.create("poop")))
            .build();
    }
}
