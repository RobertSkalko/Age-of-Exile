package com.robertx22.mine_and_slash.mmorpg.proxy;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ClientProxy implements IProxy {

    @Override
    public PlayerEntity getPlayerEntityFromContext(Supplier<Context> ctx) {
        return MinecraftClient.getInstance().player;
    }

    @Override
    public String translate(Text comp) {
        return I18n.translate(comp.asFormattedString()
            .replaceAll("%", "PERCENT"))
            .replaceAll("PERCENT", "%");
    }

}
