package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import com.robertx22.age_of_exile.areas.base_areas.BaseAreas;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.LifeCycleEvents;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.registers.common.*;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;
import top.theillusivec4.curios.api.event.DropRulesCallback;
import top.theillusivec4.curios.api.type.component.ICurio;
import top.theillusivec4.curios.api.type.component.ICuriosItemHandler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        Watch watch = new Watch();

        MapField.init();
        EffectCondition.init();

        ModRegistry.init();
        ModItemTags.init();
        MobAttributes.register();

        SlashRegistry.initRegistries();
        SlashRegistry.registerAllItems(); // after config registerAll
        GeneratedData.addAllObjectsToGenerate();

        BaseAreas.INSTANCE = new BaseAreas();
        AreaModifiers.INSTANCE = new AreaModifiers();

        CommonEvents.register();

        C2SPacketRegister.register();

        ConfigRegister.registerCustomConfigs();

        LifeCycleEvents.register();

        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("ring").size(2)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("necklace").size(1)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder(RefCurio.SALVAGE_BAG).size(1)
            .build());

        ModCriteria.init();

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        // dont drop curios on death
        DropRulesCallback.EVENT.register(new DropRulesCallback() {
            @Override
            public void dropRules(LivingEntity livingEntity, ICuriosItemHandler iCuriosItemHandler, DamageSource damageSource, int i, boolean b, List<Pair<Predicate<ItemStack>, ICurio.DropRule>> list) {

                if (ModConfig.get().Server.KEEP_INVENTORY_ON_DEATH) {
                    list.add(new Pair<Predicate<ItemStack>, ICurio.DropRule>(x -> true, ICurio.DropRule.ALWAYS_KEEP));
                }
            }
        });

        //testLevelCurve();

        /*
        Armor.getInstance()
            .logUsableAmountTests();

        DodgeRating.getInstance()
            .logUsableAmountTests();
h

         */
        // testLevelCurve();

        uncapHealth();

        watch.print("Age of Exile common init ");

    }

    public static void testLevelCurve() {
        for (int i = 1; i < ModConfig.get().Server.MAX_LEVEL + 1; i++) {

            int needed = LevelUtils.getExpRequiredForLevel(i);
            int basepermob = LevelUtils.getBaseExpMobReward(i);
            int killsneeded = needed / basepermob;
            System.out.print("\nExp needed for lvl: " + i + " is " + needed);
            System.out.print("\nBase mob xp reward for lvl: " + i + " is " + basepermob);
            System.out.print("\nKills needed for level: " + i + " is " + killsneeded);
            System.out.print("\n");
        }
    }

    static void uncapHealth() {

        boolean succeded = false;

        ClampedEntityAttribute hp = (ClampedEntityAttribute) EntityAttributes.GENERIC_MAX_HEALTH;
        for (Field field : hp.getClass()
            .getDeclaredFields()
        ) {

            try {
                field.setAccessible(true);
                double num = field.getDouble(hp);

                if (num > 1000) {
                    field.set(hp, Double.MAX_VALUE);
                    succeded = true;
                }

            } catch (IllegalArgumentException e) {
                //e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (!succeded) {
            System.out.println("Uncapping max health failed! Contact mcrobertx22/Age of Exile dev!");
        }
    }
}
