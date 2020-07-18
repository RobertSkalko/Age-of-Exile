package com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces;

import net.minecraft.entity.LivingEntity;

public interface IEffect {

	public abstract LivingEntity Source();

	public abstract LivingEntity Target();

	public abstract float Number();

}
