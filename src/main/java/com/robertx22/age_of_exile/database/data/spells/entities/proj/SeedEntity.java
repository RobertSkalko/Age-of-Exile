package com.robertx22.age_of_exile.database.data.spells.entities.proj;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.saveclasses.spells.EntitySpellData;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SeedEntity extends EntityBaseProjectile {

    public SeedEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public SeedEntity(World worldIn) {
        super(ModRegistry.ENTITIES.SEED, worldIn);
    }

    @Override
    public double radius() {
        return 0;
    }

    @Override
    protected void onImpact(HitResult ray) {

    }

    public boolean canPlace(BlockPos pos) {

        if (!world.getBlockState(pos.down())
            .isOpaque()) {
            return false;// dont spawn block unless there's solid underneath
        }

        if (!world.getBlockState(pos)
            .isAir()) {
            return false; // only replace air
        }
        return true;

    }

    @Override
    public void onTick() {

        try {

            if (!getSpellData().activated && this.getTicksInGround() > 5) {

                getSpellData().activated = true;
                this.remove();

                LivingEntity caster = getCaster();

                List<BlockPos> posToTry = new ArrayList<>();
                posToTry.add(getBlockPos());
                posToTry.add(getBlockPos().up());
                posToTry.add(getBlockPos().down());

                for (BlockPos pos : posToTry) {

                    if (canPlace(pos)) {

                        CalculatedSpellData skillgem = getSpellData().skillgem;

                        caster.world.setBlockState(pos, skillgem.getSpell()
                            .getImmutableConfigs()
                            .spellBlockToSpawn()
                            .getDefaultState());

                        BlockEntity tile = world.getBlockEntity(pos);

                        if (tile instanceof ISpellEntity) {
                            ISpellEntity se = (ISpellEntity) tile;
                            se.setSpellData(new EntitySpellData(skillgem, caster, getSpellData().configs));
                            se.initSpellEntity();
                        }

                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.WHEAT_SEEDS);
    }

}
