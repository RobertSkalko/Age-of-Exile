package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs;

import com.robertx22.mine_and_slash.database.data.spells.blocks.base.BaseSpellBlock;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell.AllowedAsRightClickOn;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellPredicate;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell.AllowedAsRightClickOn;

public abstract class ImmutableSpellConfigs {

    private RegistryObject<? extends BaseSpellBlock> block;
    private BasePotionEffect effect;
    private boolean goesOnCooldownIfCanceled;
    private Function<World, Entity> newEntitySummoner;
    private List<SpellPredicate> castRequirements = new ArrayList<>();
    private AllowedAsRightClickOn allowedAsRightClickOn = AllowedAsRightClickOn.NONE;
    private boolean swingArmOnCast = false;

    public boolean getSwingsArmOnCast() {
        return swingArmOnCast;
    }

    public ImmutableSpellConfigs setSwingArmOnCast() {
        this.swingArmOnCast = true;
        return this;
    }

    public abstract SpellCastType castType();

    public abstract SoundEvent sound();

    public abstract Elements element();

    public final BaseSpellBlock spellBlockToSpawn() {
        return block.get();
    }

    public <T extends BaseSpellBlock> ImmutableSpellConfigs spawnBlock(RegistryObject<T> block) {
        this.block = block;
        return this;
    }

    public final BasePotionEffect potionEffect() {
        return effect;
    }

    public ImmutableSpellConfigs addsEffect(BasePotionEffect effect) {
        this.effect = effect;
        return this;
    }

    public final boolean goesOnCooldownIfCanceled() {
        return goesOnCooldownIfCanceled;
    }

    public ImmutableSpellConfigs cooldownIfCanceled(boolean bool) {
        this.goesOnCooldownIfCanceled = bool;
        return this;
    }

    public final Function<World, Entity> newEntitySummoner() {
        return this.newEntitySummoner;
    }

    public ImmutableSpellConfigs summonsEntity(Function<World, Entity> sum) {
        this.newEntitySummoner = sum;
        return this;
    }

    public final List<SpellPredicate> castRequirements() {
        return castRequirements;
    }

    public ImmutableSpellConfigs addCastRequirement(SpellPredicate castRequirements) {
        this.castRequirements.add(castRequirements);
        return this;
    }

    public final AllowedAsRightClickOn allowedAsRightClickOn() {
        return this.allowedAsRightClickOn;
    }

    public ImmutableSpellConfigs rightClickFor(AllowedAsRightClickOn allowedAsRightClickOn) {
        this.allowedAsRightClickOn = allowedAsRightClickOn;
        return this;
    }
}
