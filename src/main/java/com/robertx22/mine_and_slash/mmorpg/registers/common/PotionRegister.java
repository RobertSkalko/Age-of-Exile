package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.BraveryEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.TrickeryEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.divine.WizardryEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.*;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.ShiverEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.HunterInstinctEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.ImbueEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ranger.WoundsEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.StaticEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.ThunderEssenceEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PotionRegister {

    public static void register() {

        reg(BraveryEffect.INSTANCE);
        reg(WizardryEffect.INSTANCE);
        reg(TrickeryEffect.INSTANCE);

        reg(FrostEffect.INSTANCE);
        reg(RegenerateEffect.INSTANCE);
        reg(PoisonEffect.INSTANCE);
        reg(PetrifyEffect.INSTANCE);
        reg(ShiverEffect.INSTANCE);
        reg(JudgementEffect.INSTANCE);
        reg(ThornArmorEffect.INSTANCE);
        reg(StaticEffect.INSTANCE);
        reg(BurnEffect.INSTANCE);
        reg(ThunderEssenceEffect.INSTANCE);
        reg(PoisonedWeaponsEffect.getInstance());
        reg(ImbueEffect.getInstance());
        reg(HunterInstinctEffect.getInstance());
        reg(WoundsEffect.getInstance());

    }

    static void reg(BasePotionEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, effect.GUID()), effect);

    }

}
