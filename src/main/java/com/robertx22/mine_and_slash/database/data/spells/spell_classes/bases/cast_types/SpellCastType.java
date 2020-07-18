package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;

public abstract class SpellCastType {

    public static CastAtSight AT_SIGHT = new CastAtSight();
    public static CastGiveEffect GIVE_EFFECT = new CastGiveEffect();
    public static CastSelfHeal SELF_HEAL = new CastSelfHeal();
    public static CastProjectile PROJECTILE = new CastProjectile();
    public static CastSpecial SPECIAL = new CastSpecial();
    public static CastAoeEffect AOE_EFFECT = new CastAoeEffect();

    public abstract boolean cast(SpellCastContext ctx);

}
