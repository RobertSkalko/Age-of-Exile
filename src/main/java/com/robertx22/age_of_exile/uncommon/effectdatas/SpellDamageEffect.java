package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class SpellDamageEffect extends DamageEffect implements IHasSpellEffect {

    public Spell spell;

    public SpellDamageEffect(LivingEntity source, LivingEntity target, int dmg, Spell spell) {
        super(null, source, target, dmg, spell.getConfig().style.getAttackType(), weapon(source, spell), spell.getConfig().style);
        this.spell = spell;
    }

    private static WeaponTypes weapon(LivingEntity en, Spell spell) {

        try {
            if (spell.getConfig().style.getAttackType() != AttackType.spell) {

                ItemStack stack = en.getMainHandStack();

                GearItemData gear = Gear.Load(stack);

                if (gear != null) {
                    return gear.GetBaseGearType().weapon_type;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return WeaponTypes.None;

    }

    @Override
    public Spell getSpell() {
        return spell;
    }

    @Override
    protected void activate() {
        super.activate();
    }
}
