package com.robertx22.age_of_exile.mmorpg.registers.server;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.vanilla_mc.commands.*;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.*;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GiveBuffScroll;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GiveExactUnique;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GiveGear;
import com.robertx22.age_of_exile.vanilla_mc.commands.open_gui.OpenHub;
import com.robertx22.age_of_exile.vanilla_mc.commands.reset.ResetSpellCooldowns;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ClearStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.GiveStat;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ListStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.RemoveStat;
import com.robertx22.age_of_exile.vanilla_mc.commands.test_build.TestBuild;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;

public class CommandRegister {

    public static void Register(MinecraftServer server) {
        System.out.println("Registering Age of Exile Commands.");

        CommandDispatcher<CommandSource> dispatcher = server.getCommands()
            .getDispatcher();

        GiveBuffScroll.register(dispatcher);
        GiveExactUnique.register(dispatcher);
        GiveGear.register(dispatcher);
        SetEntityRarity.register(dispatcher);
        SetLevel.register(dispatcher);
        GiveExp.register(dispatcher);
        GiveFavor.register(dispatcher);
        SetSkillLevel.register(dispatcher);

        TestBuild.register(dispatcher);

        ResetSpellCooldowns.register(dispatcher);
        SyncDatapacks.register(dispatcher);
        GenerateWikiCommand.register(dispatcher);

        GiveStat.register(dispatcher);
        RemoveStat.register(dispatcher);
        ClearStats.register(dispatcher);
        ListStats.register(dispatcher);

        TeamCommand.register(dispatcher);
        RollCommand.register(dispatcher);

        RunTestCommand.register(dispatcher);

        OpenHub.register(dispatcher);

    }
}