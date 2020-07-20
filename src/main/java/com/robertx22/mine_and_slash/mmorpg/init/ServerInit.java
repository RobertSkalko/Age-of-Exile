package com.robertx22.mine_and_slash.mmorpg.init;

import com.robertx22.mine_and_slash.mmorpg.event_registers.Server;
import net.fabricmc.api.DedicatedServerModInitializer;

public class ServerInit implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        Server.register();
    }
}
