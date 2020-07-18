package com.robertx22.mine_and_slash.vanilla_mc.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;

public class CommandRefs {
    public static String ID = "slash";

    public static IntegerArgumentType RARITY_ARG = IntegerArgumentType.integer(
        -1, 5);

}
