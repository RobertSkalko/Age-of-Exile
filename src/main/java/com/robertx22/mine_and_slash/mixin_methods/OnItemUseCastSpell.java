package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OnItemUseCastSpell {

    public static void use(World world, LivingEntity player, ItemStack stack) {
        if (player instanceof PlayerEntity) {
            if (world.isClient) {
                Packets.sendToServer(new CastSpellPacket((PlayerEntity) player));
            }
        }
    }
}
