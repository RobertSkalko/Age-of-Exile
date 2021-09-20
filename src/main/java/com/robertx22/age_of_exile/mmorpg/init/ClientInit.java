package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.ParticleFactoryRegister;

public class ClientInit {


    public static void onInitializeClient() {

        ClientSetup.setup();

        KeybindsRegister.register();
        Client.register();

        ParticleFactoryRegister.register();

    }
}
