package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Consumer;

public class BoxUtils {

    public static Vector3d positionToVelocity(Vector3d current, Vector3d destination) {
        return destination.subtract(current)
            .normalize();
    }

    public static void iterateBox(AxisAlignedBB box, Consumer<BlockPos> cons) {
        for (int x = (int) box.minX; x < box.maxX; x++) {
            for (int y = (int) box.minY; y < box.maxY; y++) {
                for (int z = (int) box.minZ; z < box.maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    cons.accept(pos);
                }
            }
        }

    }

    public static AxisAlignedBB createBoxOfRadius(BlockPos pos, int radius) {
        int num = radius;

        AxisAlignedBB box = new AxisAlignedBB(
            pos.getX() - num,
            pos.getY() - num,
            pos.getZ() - num,
            pos.getX() + num,
            pos.getY() + num,
            pos.getZ() + num);
        return box;

    }

    public static void iterateInCircle(BlockPos pos, int radius, Consumer<BlockPos> cons) {
        AxisAlignedBB box = createBoxOfRadius(pos, radius);
        iterateBoxAsCircle(box, cons);
    }

    public static void iterateBoxAsCircle(AxisAlignedBB box, Consumer<BlockPos> cons) {

        BlockPos middle = new BlockPos(box.getCenter().x, box.getCenter().y, box.getCenter().z);
        int distance = (int) (Math.abs(box.maxX - box.minX) / 2);

        iterateBox(box, x -> {
            if (middle.closerThan(x, distance)) {
                cons.accept(x);
            }
        });

    }
}
