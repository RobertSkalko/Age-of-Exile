package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class OnScreenMessageUtils {

    public static void sendLevelUpMessage(PlayerEntity player, MutableText levelType, int before, int now) {
        levelType.formatted(Formatting.GREEN)
            .formatted(Formatting.BOLD);

        ServerPlayerEntity p = (ServerPlayerEntity) player;
        p.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, new LiteralText(Formatting.YELLOW + "" + Formatting.BOLD + "Leveled Up!"), 5, 15, 8));
        p.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.SUBTITLE, levelType.append(new LiteralText(Formatting.GREEN + "" + Formatting.BOLD + " Level: " + before + " > " + now + "!")), 5, 15, 8));

        SoundUtils.ding(player.world, player.getBlockPos());
    }

    public static void sendMessage(ServerPlayerEntity p, MutableText title, MutableText sub) {
        p.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, title
            , 5, 15, 8));
        p.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.SUBTITLE, sub, 5, 15, 8));

    }

}
