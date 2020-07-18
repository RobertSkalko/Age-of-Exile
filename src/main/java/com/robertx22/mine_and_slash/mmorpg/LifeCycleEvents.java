package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.a_libraries.curios.GenerateCurioDataJsons;
import com.robertx22.mine_and_slash.auto_comp.DeterminePowerLevels;
import com.robertx22.mine_and_slash.data_generation.DimConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.EntityConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.affixes.AffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.base_gear_types.BaseGearTypeDatapackManager;
import com.robertx22.mine_and_slash.data_generation.compatible_items.CompatibleItemDataPackManager;
import com.robertx22.mine_and_slash.data_generation.mob_affixes.MobAffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.rarities.GearRarityManager;
import com.robertx22.mine_and_slash.data_generation.rarities.SkillGemRarityManager;
import com.robertx22.mine_and_slash.data_generation.tiers.TierDatapackManager;
import com.robertx22.mine_and_slash.data_generation.unique_gears.UniqueGearDatapackManager;
import com.robertx22.mine_and_slash.database.registrators.CurrencyItems;
import com.robertx22.mine_and_slash.mmorpg.registers.server.CommandRegister;
import com.robertx22.mine_and_slash.uncommon.develeper.CreateLangFile;
import com.robertx22.mine_and_slash.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.mine_and_slash.uncommon.testing.TestManager;
import com.robertx22.mine_and_slash.uncommon.testing.tests.CheckWeaponDpsBalanceTest;
import com.robertx22.mine_and_slash.uncommon.testing.tests.CountUniqueGearTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.world.GameRules;

public class LifeCycleEvents {

    static boolean regDefault = true;

    public static void register(){


        ServerLifecycleEvents.SERVER_STARTING.register(server-> {
            new CurrencyItems().registerAll();

            MMORPG.server = server;

            ReloadableResourceManager manager =server
                .getDataManager();

            manager.registerListener(new BaseGearTypeDatapackManager());
            manager.registerListener(new TierDatapackManager());
            manager.registerListener(new AffixDataPackManager());
            manager.registerListener(new MobAffixDataPackManager());
            manager.registerListener(new UniqueGearDatapackManager());
            manager.registerListener(new CompatibleItemDataPackManager());
            manager.registerListener(new GearRarityManager());
            manager.registerListener(new SkillGemRarityManager());
            manager.registerListener(new DimConfigsDatapackManager());
            manager.registerListener(new EntityConfigsDatapackManager());

        });

        ServerLifecycleEvents.SERVER_STARTED.register(server-> {

            DeterminePowerLevels.setupHashMaps();

            CommandRegister.Register(server);

            SlashRegistry.checkGuidValidity();

            ErrorChecks.getAll()
                .forEach(x -> x.check());
            SlashRegistry.unregisterInvalidEntries();

            regDefault = server
                .getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .get();

           server                .getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(false,server);

            if (MMORPG.RUN_DEV_TOOLS) { // CHANGE ON PUBLIC BUILDS TO FALSE
                TestManager.RunAllTests();
                CreateLangFile.create();
                GenerateCurioDataJsons.generate();
                CountUniqueGearTypes.count();
                CheckWeaponDpsBalanceTest.run();
            }


        });



        ServerLifecycleEvents.SERVER_STOPPED.register(server-> {
            server.getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(regDefault, server);
        });

    }
}
