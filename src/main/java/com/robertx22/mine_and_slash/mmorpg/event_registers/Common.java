package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.a_libraries.curios.OnCurioChangeEvent;
import com.robertx22.mine_and_slash.event_hooks.player.StopCastingIfInteract;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import top.theillusivec4.curios.api.event.CurioChangeCallback;

public class Common {

    public static void register() {

        AttackEntityCallback.EVENT.register(new StopCastingIfInteract());
        CurioChangeCallback.EVENT.register(new OnCurioChangeEvent());

    }

}
