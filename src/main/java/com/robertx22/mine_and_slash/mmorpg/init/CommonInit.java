package com.robertx22.mine_and_slash.mmorpg.init;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.a_libraries.curios.AddCurioCapability;
import com.robertx22.mine_and_slash.mmorpg.LifeCycleEvents;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.event_registers.Common;
import com.robertx22.mine_and_slash.mmorpg.event_registers.Server;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PotionRegister;
import net.fabricmc.api.ModInitializer;

public class CommonInit implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Starting " + Ref.MOD_NAME);

        ModRegistry.init();

        SlashRegistry.initRegistries();
        Common.register();
        ConfigRegister.registerForgeConfigs(); // MUST BE IN MAIN CLASS
        SlashRegistry.registerAllItems(); // after config registerAll
        SlashRegistry.checkGuidValidity();

        PotionRegister.register();

        //this was in common
        PacketRegister.register();
        //common

        ConfigRegister.registerCustomConfigs();

        AddCurioCapability.addComponents();

        LifeCycleEvents.register();

        Server.register();
    }
}
