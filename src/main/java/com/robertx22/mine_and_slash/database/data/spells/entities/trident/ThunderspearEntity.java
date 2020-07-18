package com.robertx22.mine_and_slash.database.data.spells.entities.trident;

import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ThunderspearEntity extends BaseTridentEntity {

    public ThunderspearEntity(World world) {
        super(EntityRegister.THUNDER_SPEAR, world);
    }

    public ThunderspearEntity(EntityType type, World world) {
        super(type, world);
    }

    public ThunderspearEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityRegister.THUNDER_SPEAR, world);
    }

}
