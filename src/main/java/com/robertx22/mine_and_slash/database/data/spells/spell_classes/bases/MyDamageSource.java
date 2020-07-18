package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;


public class MyDamageSource extends EntityDamageSource {

    public Elements element = Elements.Physical;
    public int realDamage = 0;

    @Nullable
    DamageSource source;

    public MyDamageSource(DamageSource s, String damageTypeIn, Entity source, Elements element, int dmg) {
        super(damageTypeIn, source);
        // this.setDamageBypassesArmor();
        this.element = element;
        realDamage = dmg;

        this.source = s;

    }

    @Override
    public Text getDeathMessage(LivingEntity en) {

        String type = this.name;

        if (source != null) {
            type = source.name;
        }

        ItemStack lvt_2_1_ = this.source instanceof LivingEntity ? ((LivingEntity) this.source).getMainHandStack() : ItemStack.EMPTY;
        String lvt_3_1_ = "death.attack." + this.name;
        return !lvt_2_1_.isEmpty() && lvt_2_1_.hasCustomName() ? new TranslatableText(lvt_3_1_ + ".item", new Object[]{
            en.getDisplayName(),
            this.source.getDisplayName(),
            lvt_2_1_.toHoverableText()
        }) : new TranslatableText(lvt_3_1_, new Object[]{
            en.getDisplayName(),
            this.source.getDisplayName()
        });

    }

}
