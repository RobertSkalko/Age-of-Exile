package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.mmorpg.registers.client.ClientSetup;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import net.fabricmc.api.ClientModInitializer;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientSetup.setup();

        KeybindsRegister.register();
        RegisterClientEvents.register();

    }
}
