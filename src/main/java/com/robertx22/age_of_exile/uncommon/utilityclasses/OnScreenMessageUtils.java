package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class OnScreenMessageUtils {

    public static void sendLevelUpMessage(PlayerEntity player, IFormattableTextComponent levelType, int before, int now) {
        levelType.withStyle(TextFormatting.GREEN)
            .withStyle(TextFormatting.BOLD);

        ServerPlayerEntity p = (ServerPlayerEntity) player;
        p.connection.send(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent(TextFormatting.YELLOW + "" + TextFormatting.BOLD + "Leveled Up!"), 5, 15, 8));
        p.connection.send(new STitlePacket(STitlePacket.Type.SUBTITLE, levelType.append(new StringTextComponent(TextFormatting.GREEN + "" + TextFormatting.BOLD + " Level: " + before + " > " + now + "!")), 5, 15, 8));

        SoundUtils.ding(player.level, player.blockPosition());
    }

    public static void sendMessage(ServerPlayerEntity p, IFormattableTextComponent title, IFormattableTextComponent sub) {
        p.connection.send(new STitlePacket(STitlePacket.Type.TITLE, title
            , 5, 15, 8));
        p.connection.send(new STitlePacket(STitlePacket.Type.SUBTITLE, sub, 5, 15, 8));

    }

    public static void sendMessage(ServerPlayerEntity p, IFormattableTextComponent title, STitlePacket.Type act) {
        p.connection.send(new STitlePacket(act, title
            , 5, 15, 8));
    }

}
