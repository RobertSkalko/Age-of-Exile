package com.robertx22.mine_and_slash.mmorpg.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IProxy {

    PlayerEntity getPlayerEntityFromContext(Supplier<NetworkEvent.Context> ctx);

    String translate(Text comp);

}