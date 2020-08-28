package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;

import java.util.function.Predicate;

public class SpellPredicates {
    private static Predicate<LivingEntity> SHOOTABLE_PRED = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .getTags()
                .contains(BaseGearType.SlotTag.ranger_casting_weapon);
        } catch (Exception e) {
            return false;
        }
    };

    private static Predicate<LivingEntity> MELEE_PRED = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .getTags()
                .contains(BaseGearType.SlotTag.melee_weapon);
        } catch (Exception e) {
            return false;
        }
    };

    private static Predicate<LivingEntity> MAGE_PRED = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .getTags()
                .contains(BaseGearType.SlotTag.mage_casting_weapon);
        } catch (Exception e) {
            return false;
        }
    };
    private static Predicate<LivingEntity> ANY = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .isWeapon();
        } catch (Exception e) {
            return false;
        }
    };

    public static SpellPredicate REQUIRE_SHOOTABLE = new SpellPredicate(SHOOTABLE_PRED, new SText(Formatting.GREEN + "Requires Ranged Weapon to use."));
    public static SpellPredicate REQUIRE_MAGE_WEAPON = new SpellPredicate(MAGE_PRED, new SText(Formatting.GREEN + "Requires Mage Weapon to use."));
    public static SpellPredicate REQUIRE_MELEE = new SpellPredicate(MELEE_PRED, new SText(Formatting.GOLD + "Requires Melee weapon to use."));
    public static SpellPredicate ANY_WEAPON = new SpellPredicate(ANY, new SText(Formatting.GOLD + "Any weapon can use."));

}

