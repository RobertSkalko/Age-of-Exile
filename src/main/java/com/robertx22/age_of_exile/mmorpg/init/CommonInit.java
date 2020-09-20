package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import com.robertx22.age_of_exile.areas.base_areas.BaseAreas;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.LifeCycleEvents;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.ConfigRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.MobAttributes;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModCriteria;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;

import java.lang.reflect.Field;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("Starting " + Ref.MOD_NAME);

        MapField.init();
        EffectCondition.init();

        ModRegistry.init();
        MobAttributes.register();

        SlashRegistry.initRegistries();
        SlashRegistry.registerAllItems(); // after config registerAll
        SlashRegistry.checkGuidValidity();

        BaseAreas.INSTANCE = new BaseAreas();
        AreaModifiers.INSTANCE = new AreaModifiers();

        CommonEvents.register();

        //this was in common
        C2SPacketRegister.register();
        //common

        ConfigRegister.registerCustomConfigs();

        LifeCycleEvents.register();

        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("ring").size(2)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("necklace").size(1)
            .build());

        ModCriteria.init();

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        uncapHealth();
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
