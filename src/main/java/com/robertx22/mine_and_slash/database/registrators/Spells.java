package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.HealingAuraSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.HolyFlowerSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.PurifyingFiresSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.SpearOfJudgementSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs.BraverySpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs.TrickerySpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.divine.buffs.WizardrySpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.fire.*;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.hunting.*;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature.*;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.ocean.*;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.storm.LightningTotemSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.storm.ThunderDashSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.storm.ThunderspearSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.storm.ThunderstormSpell;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class Spells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        List<BaseSpell> All = new ArrayList<BaseSpell>() {
            {
                {

                    add(BraverySpell.getInstance());
                    add(TrickerySpell.getInstance());
                    add(WizardrySpell.getInstance());

                    add(HolyFlowerSpell.getInstance());
                    add(SpearOfJudgementSpell.getInstance());
                    add(PurifyingFiresSpell.getInstance());
                    add(HealingAuraSpell.getInstance());

                    add(FrostballSpell.getInstance());
                    add(WhirlpoolSpell.getInstance());
                    add(HeartOfIceSpell.getInstance());
                    add(TidalWaveSpell.getInstance());
                    add(BlizzardSpell.getInstance());

                    add(NatureBalmSpell.getInstance());
                    add(GorgonsGazeSpell.getInstance());
                    add(ThornArmorSpell.getInstance());
                    add(ThornBushSpell.getInstance());
                    add(PoisonedWeaponsSpell.getInstance());
                    add(PoisonBallSpell.getInstance());

                    add(ThunderstormSpell.getInstance());
                    add(ThunderspearSpell.getInstance());
                    add(ThunderDashSpell.getInstance());
                    add(LightningTotemSpell.getInstance());

                    add(BlazingInfernoSpell.getInstance());
                    add(FireballSpell.getInstance());
                    add(VolcanoSpell.getInstance());
                    add(MagmaFlowerSpell.getInstance());
                    add(ThrowFlamesSpell.getInstance());
                    add(FireBombsSpell.getInstance());

                    add(ArrowBarrageSpell.getInstance());
                    add(RecoilShotSpell.getInstance());
                    add(MultiShotSpell.getInstance());
                    add(ImbueSpell.getInstance());
                    add(ArrowStormSpell.getInstance());

                }
            }

        };

        All.forEach(x -> x.registerToSlashRegistry());

    }
}
