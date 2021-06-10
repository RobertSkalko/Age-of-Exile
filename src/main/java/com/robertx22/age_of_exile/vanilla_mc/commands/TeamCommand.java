package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.capability.PlayerDamageChart;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TeamCommand {

    public static void listMembers(PlayerEntity player) {
        List<PlayerEntity> players = TeamUtils.getOnlineMembers(player);

        player.sendMessage(new LiteralText("Team members:"), false);

        players.forEach(e -> {
            player.sendMessage(e.getDisplayName(), false);
        });
    }

    public static void sendDpsCharts(PlayerEntity player) {
        List<PlayerEntity> members = TeamUtils.getOnlineMembers(player);

        player.sendMessage(new LiteralText("Damage Charts:"), false);

        members.forEach(e -> {
            Text text = new LiteralText("").append(e.getDisplayName())
                .append(": " + (int) PlayerDamageChart.getDamage(e));

            player.sendMessage(text, false);

        });

    }

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("teams").requires(e -> e.hasPermissionLevel(0))

                    .then(literal("create").executes(x -> {
                        Load.team(x.getSource()
                            .getPlayer())
                            .createTeam();
                        return 0;
                    }))
                    .then(literal("leave").executes(x -> {
                        Load.team(x.getSource()
                            .getPlayer())
                            .leaveTeam();
                        return 0;
                    }))
                    .then(literal("dps_chart").executes(x -> {

                        PlayerEntity player = x.getSource()
                            .getPlayer();

                        sendDpsCharts(player);
                        return 0;
                    }))
                    .then(literal("list_members").executes(x -> {

                        PlayerEntity player = x.getSource()
                            .getPlayer();
                        listMembers(player);

                        return 0;
                    }))
                    .then(literal("invite")
                        .then(argument("player", EntityArgumentType.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayer();

                                if (!Load.team(player)
                                    .isOnTeam()) {
                                    player.sendMessage(new LiteralText("You are not in a team."), false);
                                    return 0;
                                }

                                PlayerEntity other = EntityArgumentType.getPlayer(x, "player");

                                other.sendMessage(new LiteralText("").append(player.getDisplayName())
                                    .append(" has invited you to a team."), false);
                                other.sendMessage(new LiteralText("Do /age_of_exile teams join ").append(player.getDisplayName())
                                    .append(" to accept"), false);

                                Load.team(other).invitedToTeam = Load.team(player).team_id;

                                player.sendMessage(new LiteralText("You have invited ").append(other.getDisplayName())
                                    .append(new LiteralText(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("make_leader")
                        .then(argument("player", EntityArgumentType.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayer();

                                PlayerEntity other = EntityArgumentType.getPlayer(x, "player");

                                if (!Load.team(player)
                                    .isOnSameTeam(other)) {
                                    player.sendMessage(new LiteralText("They aren't on your team"), false);
                                    return 0;
                                }

                                player.sendMessage(new LiteralText("").append(player.getDisplayName())
                                    .append(" is now a team leader."), false);

                                player.sendMessage(new LiteralText("You have been made a team leader."), false);

                                Load.team(other).isLeader = true;

                                player.sendMessage(new LiteralText("You have invited ").append(other.getDisplayName())
                                    .append(new LiteralText(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("kick")
                        .then(argument("player", EntityArgumentType.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayer();

                                PlayerEntity other = EntityArgumentType.getPlayer(x, "player");

                                if (!Load.team(player).isLeader) {
                                    player.sendMessage(new LiteralText("You can't kick members because you aren't the leader."), false);
                                    return 0;
                                }
                                if (!Load.team(player)
                                    .isOnSameTeam(other)) {
                                    player.sendMessage(new LiteralText("They aren't on your team"), false);
                                    return 0;
                                }

                                Load.team(other).team_id = "";

                                other.sendMessage(new LiteralText("You have been kicked from your team."), false);

                                player.sendMessage(new LiteralText("You have kicked ").append(other.getDisplayName())
                                    .append(" from your team."), false);

                                return 0;
                            })

                        ))
                    .then(literal("join")
                        .then(argument("player", EntityArgumentType.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayer();

                                PlayerEntity other = EntityArgumentType.getPlayer(x, "player");

                                if (Load.team(other)
                                    .isOnTeam()) {

                                    if (Load.team(player).invitedToTeam.equals(Load.team(other).team_id)) {
                                        Load.team(x.getSource()
                                            .getPlayer())
                                            .joinTeamOf(other);

                                        player.sendMessage(new LiteralText("You have joined ").append(other.getDisplayName())
                                            .append(new LiteralText("'s team.")), false);
                                    } else {
                                        player.sendMessage(new LiteralText("You aren't invited to their team"), false);
                                    }

                                } else {
                                    x.getSource()
                                        .getPlayer()
                                        .sendMessage(new LiteralText("Player isn't in a team"), false);
                                }
                                return 0;
                            })

                        ))));
    }

}
