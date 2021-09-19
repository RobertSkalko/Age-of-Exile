package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.capability.PlayerDamageChart;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class TeamCommand {

    public static void listMembers(PlayerEntity player) {
        List<PlayerEntity> players = TeamUtils.getOnlineMembers(player);

        player.displayClientMessage(new StringTextComponent("Team members:"), false);

        players.forEach(e -> {
            player.displayClientMessage(e.getDisplayName(), false);
        });
    }

    public static void sendDpsCharts(PlayerEntity player) {
        List<PlayerEntity> members = TeamUtils.getOnlineMembers(player);

        player.displayClientMessage(new StringTextComponent("Damage Charts:"), false);

        members.forEach(e -> {
            ITextComponent text = new StringTextComponent("").append(e.getDisplayName())
                .append(": " + (int) PlayerDamageChart.getDamage(e));

            player.displayClientMessage(text, false);

        });

    }

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("teams").requires(e -> e.hasPermission(0))

                    .then(literal("create").executes(x -> {
                        Load.playerRPGData(x.getSource()
                                .getPlayerOrException()).team
                            .createTeam();
                        return 0;
                    }))
                    .then(literal("leave").executes(x -> {
                        Load.playerRPGData(x.getSource()
                                .getPlayerOrException()).team
                            .leaveTeam();
                        return 0;
                    }))
                    .then(literal("dps_chart").executes(x -> {

                        PlayerEntity player = x.getSource()
                            .getPlayerOrException();

                        sendDpsCharts(player);
                        return 0;
                    }))
                    .then(literal("list_members").executes(x -> {

                        PlayerEntity player = x.getSource()
                            .getPlayerOrException();
                        listMembers(player);

                        return 0;
                    }))
                    .then(literal("invite")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayerOrException();

                                if (!Load.playerRPGData(player).team
                                    .isOnTeam()) {
                                    player.displayClientMessage(new StringTextComponent("You are not in a team."), false);
                                    return 0;
                                }

                                PlayerEntity other = EntityArgument.getPlayer(x, "player");

                                other.displayClientMessage(new StringTextComponent("").append(player.getDisplayName())
                                    .append(" has invited you to a team."), false);
                                other.displayClientMessage(new StringTextComponent("Do /age_of_exile teams join ").append(player.getDisplayName())
                                    .append(" to accept"), false);

                                Load.playerRPGData(other).team.invitedToTeam = Load.playerRPGData(player).team.team_id;

                                player.displayClientMessage(new StringTextComponent("You have invited ").append(other.getDisplayName())
                                    .append(new StringTextComponent(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("make_leader")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayerOrException();

                                PlayerEntity other = EntityArgument.getPlayer(x, "player");

                                if (!Load.playerRPGData(player).team
                                    .isOnSameTeam(other)) {
                                    player.displayClientMessage(new StringTextComponent("They aren't on your team"), false);
                                    return 0;
                                }

                                player.displayClientMessage(new StringTextComponent("").append(player.getDisplayName())
                                    .append(" is now a team leader."), false);

                                player.displayClientMessage(new StringTextComponent("You have been made a team leader."), false);

                                Load.playerRPGData(other).team.isLeader = true;

                                player.displayClientMessage(new StringTextComponent("You have invited ").append(other.getDisplayName())
                                    .append(new StringTextComponent(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("kick")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayerOrException();

                                PlayerEntity other = EntityArgument.getPlayer(x, "player");

                                if (!Load.playerRPGData(player).team.isLeader) {
                                    player.displayClientMessage(new StringTextComponent("You can't kick members because you aren't the leader."), false);
                                    return 0;
                                }
                                if (!Load.playerRPGData(player).team
                                    .isOnSameTeam(other)) {
                                    player.displayClientMessage(new StringTextComponent("They aren't on your team"), false);
                                    return 0;
                                }

                                Load.playerRPGData(other).team.team_id = "";

                                other.displayClientMessage(new StringTextComponent("You have been kicked from your team."), false);

                                player.displayClientMessage(new StringTextComponent("You have kicked ").append(other.getDisplayName())
                                    .append(" from your team."), false);

                                return 0;
                            })

                        ))
                    .then(literal("join")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                PlayerEntity player = x.getSource()
                                    .getPlayerOrException();

                                PlayerEntity other = EntityArgument.getPlayer(x, "player");

                                if (Load.playerRPGData(other).team
                                    .isOnTeam()) {

                                    if (Load.playerRPGData(player).team.invitedToTeam.equals(Load.playerRPGData(other).team.team_id)) {
                                        Load.playerRPGData(x.getSource()
                                                .getPlayerOrException()).team
                                            .joinTeamOf(other);

                                        player.displayClientMessage(new StringTextComponent("You have joined ").append(other.getDisplayName())
                                            .append(new StringTextComponent("'s team.")), false);
                                    } else {
                                        player.displayClientMessage(new StringTextComponent("You aren't invited to their team"), false);
                                    }

                                } else {
                                    x.getSource()
                                        .getPlayerOrException()
                                        .displayClientMessage(new StringTextComponent("Player isn't in a team"), false);
                                }
                                return 0;
                            })

                        ))));
    }

}
