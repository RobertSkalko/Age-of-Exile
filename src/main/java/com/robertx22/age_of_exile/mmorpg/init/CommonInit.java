package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.LifeCycleEvents;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.MobAttributes;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModCriteria;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModItemTags;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;
import top.theillusivec4.curios.api.event.DropRulesCallback;
import top.theillusivec4.curios.api.type.component.ICurio;
import top.theillusivec4.curios.api.type.component.ICuriosItemHandler;

import java.util.List;
import java.util.function.Predicate;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        Watch watch = new Watch();

        MapField.init();
        EffectCondition.init();

        ModRegistry.init();

        ModItemTags.init();
        MobAttributes.register();

        Database.initRegistries();
        Database.registerAllItems(); // after config registerAll
        GeneratedData.addAllObjectsToGenerate();

        CommonEvents.register();

        C2SPacketRegister.register();

        LifeCycleEvents.register();

        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("ring").size(2)
            .build());
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, new SlotTypeInfo.Builder("necklace").size(1)
            .build());

        ModCriteria.init();

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        // dont drop curios on death
        DropRulesCallback.EVENT.register(new DropRulesCallback() {
            @Override
            public void dropRules(LivingEntity livingEntity, ICuriosItemHandler iCuriosItemHandler, DamageSource damageSource, int i, boolean b, List<Pair<Predicate<ItemStack>, ICurio.DropRule>> list) {

                //if (ModConfig.get().Server.KEEP_INVENTORY_ON_DEATH) {
                // list.add(new Pair<Predicate<ItemStack>, ICurio.DropRule>(x -> true, ICurio.DropRule.ALWAYS_KEEP));
                // }
            }
        });


        /*
        Armor.getInstance()
            .logUsableAmountTests();
        DodgeRating.getInstance()
            .logUsableAmountTests();h
         */

        watch.print("Age of Exile mod initialization ");

    }

}
