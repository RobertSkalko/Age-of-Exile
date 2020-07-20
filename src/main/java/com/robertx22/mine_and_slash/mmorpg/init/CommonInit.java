package com.robertx22.mine_and_slash.mmorpg.init;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.mmorpg.LifeCycleEvents;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.event_registers.Common;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PotionRegister;
import net.fabricmc.api.ModInitializer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;

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

        LifeCycleEvents.register();

        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("ring").size(2)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("necklace").size(1)
            .build());

    }
}
