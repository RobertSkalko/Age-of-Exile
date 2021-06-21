package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ExileDBInit;
import com.robertx22.age_of_exile.dimension.DimensionInit;
import com.robertx22.age_of_exile.mmorpg.LifeCycleEvents;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.MobAttributes;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModCriteria;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModItemTags;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.utils.Watch;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        Watch watch = new Watch();

        ExileEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.register(new EventConsumer<ExileEvents.OnCheckIsDevToolsRunning>() {
            @Override
            public void accept(ExileEvents.OnCheckIsDevToolsRunning event) {
                event.run = MMORPG.RUN_DEV_TOOLS;
            }
        });

        StatEffects.loadClass();
        StatConditions.loadClass();
        Stats.loadClass();
        ExileDBInit.initRegistries();
        SpecialStats.init();

        MapField.init();
        EffectCondition.init();

        ModRegistry.init();

        ModItemTags.init();
        MobAttributes.register();

        ExileDBInit.registerAllItems(); // after config registerAll

        if (MMORPG.RUN_DEV_TOOLS) {
            GeneratedData.addAllObjectsToGenerate();
        }

        CommonEvents.register();

        C2SPacketRegister.register();

        LifeCycleEvents.register();

        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("ring").size(2)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("necklace").size(1)
            .build());

        ModCriteria.init();

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        DimensionInit.init();


        /*
        Armor.getInstance()
            .logUsableAmountTests();
        DodgeRating.getInstance()
            .logUsableAmountTests();h
         */

        watch.print("Age of Exile mod initialization ");

    }

}
