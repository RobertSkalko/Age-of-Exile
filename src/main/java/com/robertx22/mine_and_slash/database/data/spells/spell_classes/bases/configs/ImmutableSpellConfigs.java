package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs;

import com.robertx22.mine_and_slash.database.data.spells.blocks.base.BaseSpellBlock;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell.AllowedAsRightClickOn;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellPredicate;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ImmutableSpellConfigs {

    private BaseSpellBlock block;
    private BasePotionEffect effect;
    private boolean goesOnCooldownIfCanceled;
    private Function<World, Entity> newEntitySummoner;
    private List<SpellPredicate> castRequirements = new ArrayList<>();
    public AllowedAsRightClickOn allowedAsRightClickOn = AllowedAsRightClickOn.MAGE_WEAPON;
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
        return block;
    }

    public <T extends BaseSpellBlock> ImmutableSpellConfigs spawnBlock(T block) {
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

}
