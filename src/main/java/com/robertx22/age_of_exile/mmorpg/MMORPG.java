package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.a_libraries.curios.CurioEvents;
import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SynergyStats;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ExileDBInit;
import com.robertx22.age_of_exile.dimension.DimensionInit;
import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsaw;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.init.ClientInit;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModItemTags;
import com.robertx22.divine_missions_addon.DMRegInit;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.ForgeEvents;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;

public class MMORPG {

    public MMORPG() {
        Watch watch = new Watch();

        ExileEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.register(new EventConsumer<ExileEvents.OnCheckIsDevToolsRunning>() {
            @Override
            public void accept(ExileEvents.OnCheckIsDevToolsRunning event) {
                event.run = MMORPG.RUN_DEV_TOOLS;
            }
        });

        final IEventBus bus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(ClientInit::onInitializeClient);
        });

        CurioEvents.reg();

        StatEffects.loadClass();
        StatConditions.loadClass();
        Stats.loadClass();
        SynergyStats.loadClass();
        ExileDBInit.initRegistries();
        SpecialStats.init();

        MapField.init();
        EffectCondition.init();

        ModRegistry.init();

        ModItemTags.init();

        ExileDBInit.registerAllItems(); // after config registerAll
        DMRegInit.init();

        if (MMORPG.RUN_DEV_TOOLS) {
            GeneratedData.addAllObjectsToGenerate();
        }

        CommonEvents.register();

        C2SPacketRegister.register();

        LifeCycleEvents.register();


        ForgeEvents.registerForgeEvent(InterModEnqueueEvent.class, event -> {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2)
                    .build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(1)
                    .build());
        });

        
        DimensionInit.init();

        DungeonDimensionJigsaw.initStatics();
        DungeonDimensionJigsaw jig = new DungeonDimensionJigsaw();
        jig.register();

        watch.print("Age of Exile mod initialization ");

    }


    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;

    public static void devToolsLog(String string) {
        if (RUN_DEV_TOOLS) {
            System.out.println(string);
        }
    }

    public static void devToolsErrorLog(String string) {
        if (RUN_DEV_TOOLS) {
            try {
                throw new Exception(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void logError(String s) {
        try {
            throw new Exception(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MinecraftServer server = null;

}
