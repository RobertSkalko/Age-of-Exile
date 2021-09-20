package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.smithing.SmithingContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.tablet.TabletStationContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ModContainers {

    public static void init() {

    }

    public static RegObj<ContainerType<ContainerGearSalvage>> SALVAGE = Def.container("salvage", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new ContainerGearSalvage(n, pinv, buf)));
    public static RegObj<ContainerType<RuneWordStationContainer>> RUNEWORD = Def.container("runeword", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new RuneWordStationContainer(n, pinv, buf)));
    public static RegObj<ContainerType<CookingContainer>> COOKING_STATION = Def.container("cooking", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new CookingContainer(n, pinv, buf)));
    public static RegObj<ContainerType<TabletStationContainer>> TABLET_STATION = Def.container("tablet", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new TabletStationContainer(n, pinv, buf)));
    public static RegObj<ContainerType<AlchemyContainer>> ALCHEMY_STATION = Def.container("alchemy", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new AlchemyContainer(n, pinv, buf)));
    public static RegObj<ContainerType<SmithingContainer>> SMITHING_STATION = Def.container("smithing", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new SmithingContainer(n, pinv, buf)));
    public static RegObj<ContainerType<BackpackContainer>> BACKPACK = Def.container("salvage", IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new BackpackContainer(n, pinv, buf)));

}
