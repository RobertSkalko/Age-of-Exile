package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackContainer;
import com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch.MatBagContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class SlashContainers {

    public static void init() {

    }

    public static RegObj<ContainerType<ContainerGearSalvage>> SALVAGE = Def.container("salvage", () -> IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new ContainerGearSalvage(n, pinv, buf)));
    public static RegObj<ContainerType<RuneWordStationContainer>> RUNEWORD = Def.container("runeword", () -> IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new RuneWordStationContainer(n, pinv, buf)));
    public static RegObj<ContainerType<BackpackContainer>> BACKPACK = Def.container("backpack", () -> IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new BackpackContainer(n, pinv, buf)));
    public static RegObj<ContainerType<MatBagContainer>> MAT_POUCH = Def.container("pouch", () -> IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new MatBagContainer(n, pinv, buf)));

}
