package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.ClothSlippers;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.OccultistRobes;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.SilkPants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.SorcererCirclet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.HunterHood;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.LeatherLeggings;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.RawhideBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.WildTunic;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.Buckler;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.SpiritShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.TowerShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronChestplate;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronGreaves;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.IronLegguards;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.Crossbow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.HunterBow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.GemstoneSword;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.PrimitiveAxe;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.SageWand;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class BaseGearTypes implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        List<BaseGearType> All = new ArrayList<BaseGearType>() {
            {
                {
                    add(IronGreaves.INSTANCE);
                    add(IronLegguards.INSTANCE);
                    add(IronHelmet.INSTANCE);
                    add(IronChestplate.INSTANCE);

                    add(RawhideBoots.INSTANCE);
                    add(LeatherLeggings.INSTANCE);
                    add(HunterHood.INSTANCE);
                    add(WildTunic.INSTANCE);

                    add(ClothSlippers.INSTANCE);
                    add(OccultistRobes.INSTANCE);
                    add(SorcererCirclet.INSTANCE);
                    add(SilkPants.INSTANCE);

                    add(TowerShield.INSTANCE);
                    add(SpiritShield.INSTANCE);
                    add(Buckler.INSTANCE);

                    add(OccultRing.INSTANCE);
                    add(LifeRing.INSTANCE);
                    add(LifeNecklace.INSTANCE);
                    add(OccultNecklace.INSTANCE);

                    add(HunterBow.INSTANCE);
                    add(SageWand.INSTANCE);
                    add(PrimitiveAxe.INSTANCE);
                    add(Crossbow.INSTANCE);
                    add(GemstoneSword.INSTANCE);

                }

            }
        };

        All.forEach(x -> x.addToSerializables());

    }
}
