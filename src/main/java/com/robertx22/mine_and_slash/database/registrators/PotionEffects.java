package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.*;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.ColdEssenceEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.ShiverEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.HunterInstinctEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.ImbueEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.WoundsEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.StaticEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.ThunderEssenceEffect;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;

public class PotionEffects implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PetrifyEffect.INSTANCE.registerToSlashRegistry();
        PoisonedWeaponsEffect.getInstance()
            .registerToSlashRegistry();
        RegenerateEffect.INSTANCE.registerToSlashRegistry();
        ThornArmorEffect.INSTANCE.registerToSlashRegistry();
        PoisonEffect.INSTANCE.registerToSlashRegistry();

        BurnEffect.INSTANCE.registerToSlashRegistry();

        ColdEssenceEffect.INSTANCE.registerToSlashRegistry();
        FrostEffect.INSTANCE.registerToSlashRegistry();
        ShiverEffect.INSTANCE.registerToSlashRegistry();
        JudgementEffect.INSTANCE.registerToSlashRegistry();

        HunterInstinctEffect.getInstance()
            .registerToSlashRegistry();
        ImbueEffect.getInstance()
            .registerToSlashRegistry();
        WoundsEffect.getInstance()
            .registerToSlashRegistry();

        StaticEffect.INSTANCE.registerToSlashRegistry();
        ThunderEssenceEffect.INSTANCE.registerToSlashRegistry();

    }
}
