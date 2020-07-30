package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Formatting;

import java.util.function.Predicate;

public class SpellPredicates {
    private static Predicate<LivingEntity> SHOOTABLE_PRED = x -> {
        Item item = x.getMainHandStack()
            .getItem();
        return item instanceof RangedWeaponItem;
    };

    private static Predicate<LivingEntity> MELEE_PRED = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .isMeleeWeapon();
        } catch (Exception e) {
            return false;
        }
    };
    private static Predicate<LivingEntity> MAGE_PRED = x -> {
        try {
            GearItemData data = Gear.Load(x.getMainHandStack());
            return data != null && data.GetBaseGearType()
                .isMageWeapon();
        } catch (Exception e) {
            return false;
        }
    };

    public static SpellPredicate REQUIRE_SHOOTABLE = new SpellPredicate(SHOOTABLE_PRED, new SText(Formatting.GREEN + "Requires Ranged Weapon to use: "));
    public static SpellPredicate REQUIRE_MAGE_WEAPON = new SpellPredicate(MAGE_PRED, new SText(Formatting.GREEN + "Requires Mage Weapon to use: "));
    public static SpellPredicate REQUIRE_MELEE = new SpellPredicate(MELEE_PRED, new SText(Formatting.GOLD + "Requires Melee weapon to use: "));
}

