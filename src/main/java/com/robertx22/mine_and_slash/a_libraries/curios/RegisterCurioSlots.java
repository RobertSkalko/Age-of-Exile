package com.robertx22.mine_and_slash.a_libraries.curios;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

public class RegisterCurioSlots {

    public static final String CURIOS = "curios";

    public static void register(final InterModEnqueueEvent event) {

        InterModComms.sendTo(CURIOS, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage(RefCurio.NECKLACE));
        InterModComms.sendTo(CURIOS, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage(RefCurio.RING)
            .setSize(2));

    }

}




