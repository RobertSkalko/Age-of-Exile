package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs;

import com.robertx22.mine_and_slash.database.data.spells.blocks.base.BaseSpellBlock;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell.CastingWeapon;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

import java.util.function.Function;

public abstract class ImmutableSpellConfigs {

    private BaseSpellBlock block;
    private BasePotionEffect effect;
    private boolean goesOnCooldownIfCanceled;
    private Function<World, Entity> newEntitySummoner;
    public CastingWeapon castingWeapon = CastingWeapon.MAGE_WEAPON;
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

    public ImmutableSpellConfigs castingWeapon(CastingWeapon wep) {
        this.castingWeapon = wep;
        return this;
    }

}
