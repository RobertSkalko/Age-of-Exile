package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.mmorpg.TESTRECIPETOOLTIPMOD;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {
        // test mod todo
        TESTRECIPETOOLTIPMOD.registerEvent();

        ClientSetup.setup();
        KeybindsRegister.register();
        Client.register();

    }
}
