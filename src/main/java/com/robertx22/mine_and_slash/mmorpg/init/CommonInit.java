package com.robertx22.mine_and_slash.mmorpg.init;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.mmorpg.LifeCycleEvents;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.mmorpg.event_registers.Common;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PotionRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.UniqueGearItemRegister;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;

public class CommonInit implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Starting " + Ref.MOD_NAME);

        ModRegistry.init();

        SlashRegistry.initRegistries();
        SlashRegistry.registerAllItems(); // after config registerAll
        SlashRegistry.checkGuidValidity();

        UniqueGearItemRegister.registerAll();

        Common.register();

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

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
}
