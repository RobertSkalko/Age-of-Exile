package com.robertx22.mine_and_slash.database.data.spells.entities.proj;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
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
        super(EntityRegister.SEED, worldIn);

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

                        BaseSpell spell = getSpellData().getSpell();

                        caster.world.setBlockState(pos, spell.getImmutableConfigs()
                            .spellBlockToSpawn()
                            .getDefaultState());

                        BlockEntity tile = world.getBlockEntity(pos);

                        if (tile instanceof ISpellEntity) {
                            ISpellEntity se = (ISpellEntity) tile;
                            se.setSpellData(new EntitySpellData(spell, caster, getSpellData().configs));
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
