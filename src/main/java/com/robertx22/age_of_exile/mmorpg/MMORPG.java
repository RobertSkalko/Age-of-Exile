package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.a_libraries.curios.CurioEvents;
import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SynergyStats;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ExileDBInit;
import com.robertx22.age_of_exile.dimension.DimensionInit;
import com.robertx22.age_of_exile.event_hooks.player.ScalingDifficultyEvents;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.init.ClientInit;
import com.robertx22.age_of_exile.mmorpg.registers.client.S2CPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashCapabilities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashItemTags;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.SlashDeferred;
import com.robertx22.divine_missions_addon.DMRegInit;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(SlashRef.MODID)
public class MMORPG {

    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(SlashRef.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public MMORPG() {
        Watch watch = new Watch();

        ModLoadingContext.get()
            .registerConfig(ModConfig.Type.COMMON, ServerContainer.commonSpec);

        ExileEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.register(new EventConsumer<ExileEvents.OnCheckIsDevToolsRunning>() {
            @Override
            public void accept(ExileEvents.OnCheckIsDevToolsRunning event) {
                event.run = MMORPG.RUN_DEV_TOOLS;
            }
        });

        final IEventBus bus = FMLJavaModLoadingContext.get()
            .getModEventBus();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            ModLoadingContext.get()
                .registerConfig(ModConfig.Type.CLIENT, ClientConfigs.clientSpec);
            bus.addListener(ClientInit::onInitializeClient);
        });

        bus.addListener(this::commonSetupEvent);
        bus.addListener(this::interMod);

        CurioEvents.reg();

        ScalingDifficultyEvents.register();

        StatEffects.loadClass();
        StatConditions.loadClass();
        Stats.loadClass();
        SynergyStats.loadClass();
        ExileDBInit.initRegistries();
        SpecialStats.init();

        SlashDeferred.registerDefferedAtStartOfModLoading();

        MapField.init();
        EffectCondition.init();

        SlashItemTags.init();

        ExileDBInit.registerAllItems(); // after config registerAll
        DMRegInit.init();

        CommonEvents.register();

        C2SPacketRegister.register();
        S2CPacketRegister.register();

        LifeCycleEvents.register();

        DimensionInit.init();

        // DungeonDimensionJigsaw.initStatics();
        // DungeonDimensionJigsaw test = new DungeonDimensionJigsaw();
        // test.initFeature();
        // WorldOfExile.registerStructure(test);

        watch.print("Age of Exile mod initialization ");

    }

    public void interMod(InterModEnqueueEvent event) {

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2)
            .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(1)
            .build());
    }

    public void commonSetupEvent(FMLCommonSetupEvent event) {

        if (MMORPG.RUN_DEV_TOOLS) {
            GeneratedData.addAllObjectsToGenerate();
        }

        SlashCapabilities.register();
    }

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
