package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.unit.GearData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class PlayerUtils {

    public static List<ItemStack> getEquippedStacksOf(PlayerEntity player, BaseGearType type) {

        if (type.getVanillaSlotType() == null) {
            List<ItemStack> list = new ArrayList<>();
            if (type.gear_slot.equals(RefCurio.RING)) {
                list.addAll(MyCurioUtils.getAllSlots(Arrays.asList(RefCurio.RING), player));
            }
            if (type.gear_slot.equals(RefCurio.NECKLACE)) {
                list.addAll(MyCurioUtils.getAllSlots(Arrays.asList(RefCurio.NECKLACE), player));
            }
            return list;
        } else {
            return Arrays.asList(player.getEquippedStack(type.getVanillaSlotType()));
        }

    }

    public static ItemStack lowestDurabilityWornGear(PlayerEntity player) {
        List<GearData> stacks = CollectGearEvent.getAllGear(null, player, Load.Unit(player));

        Optional<GearData> opt = stacks.stream()
            .filter(x -> !x.stack.isEmpty())
            .sorted(Comparator.comparingInt(x -> x.stack.getMaxDamage() - x.stack.getDamage()))
            .findFirst();

        if (opt.isPresent()) {
            return opt.get().stack;
        } else {
            return ItemStack.EMPTY;
        }

    }

    public static void giveItem(ItemStack stack, PlayerEntity player) {
        if (player.giveItemStack(stack) == false) {
            player.dropStack(stack, 1F);
        }
        player.inventory.markDirty();
    }

    public static PlayerEntity nearestPlayer(ServerWorld world, LivingEntity entity) {
        return nearestPlayer(world, entity.getPos());
    }

    public static PlayerEntity nearestPlayer(ServerWorld world, BlockPos pos) {
        return nearestPlayer(world, new Vec3d(pos.getY(), pos.getY(), pos.getZ()));
    }

    public static PlayerEntity nearestPlayer(ServerWorld world, Vec3d pos) {

        Optional<ServerPlayerEntity> player = world.getPlayers()
            .stream()
            .min(Comparator.comparingDouble(x -> x.squaredDistanceTo(pos)));

        return player.orElse(null);
    }

}
