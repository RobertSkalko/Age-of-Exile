package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses.AngerEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses.ChannelEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.BraveryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.TrickeryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.WizardryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.*;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.HealthRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.MagicShieldFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.ManaRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic.ShiverEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.HunterInstinctEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.ImbueEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.WoundsEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.shaman.StaticEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.shaman.ThunderEssenceEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PotionRegister {

    public static Identifier FOOD_HP = new Identifier(Ref.MODID, "food_health_regen");
    public static Identifier FOOD_MANA = new Identifier(Ref.MODID, "food_mana_regen");
    public static Identifier FOOD_MAGIC_REGEN = new Identifier(Ref.MODID, "food_magic_shield_regen");

    HashMap<Integer, ExileStatusEffect> exileEffectsMap = new HashMap<>();

    public ExileStatusEffect getExileEffectByNumber(int num) {
        return exileEffectsMap.get(num);
    }

    public PotionRegister() {

        if (MMORPG.RUN_DEV_TOOLS) { // TODO
            for (int i = 0; i < 20; i++) {
                ExileStatusEffect eff = Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, i + ""), new ExileStatusEffect(i));
                exileEffectsMap.put(i, eff);
            }
        }

        reg(BraveryEffect.INSTANCE);
        reg(WizardryEffect.INSTANCE);
        reg(TrickeryEffect.INSTANCE);

        reg(ChannelEffect.GOOD_FOR_BOSS);
        reg(ChannelEffect.BAD_FOR_PLAYER);

        reg(AngerEffect.INSTANCE);
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

        Registry.register(Registry.STATUS_EFFECT, FOOD_HP, HealthRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MANA, ManaRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MAGIC_REGEN, MagicShieldFoodEffect.INSTANCE);

    }

    void reg(BasePotionEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, effect.GUID()), effect);
    }

}
