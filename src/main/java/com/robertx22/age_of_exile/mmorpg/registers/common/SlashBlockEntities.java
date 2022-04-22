package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.tileentity.TileEntityType;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<TileEntityType<TeleportedBlockEntity>> TELEPORTER = Def.blockEntity("teleporter", () -> TileEntityType.Builder.of(TeleportedBlockEntity::new, SlashBlocks.TELEPORTER.get())
        .build(null));
    public static RegObj<TileEntityType<PortalBlockEntity>> PORTAL = Def.blockEntity("portal", () -> TileEntityType.Builder.of(PortalBlockEntity::new, SlashBlocks.PORTAL.get())
        .build(null));
    public static RegObj<TileEntityType<ModSpawnerBlockEntity>> SPAWNER = Def.blockEntity("spawner", () -> TileEntityType.Builder.of(ModSpawnerBlockEntity::new, SlashBlocks.SPAWNER.get())
        .build(null));

}
