package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.tiers.impl.*;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;

public class Tiers implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        new TierZero().addToSerializables();
        new TierOne().addToSerializables();
        new TierTwo().addToSerializables();
        new TierThree().addToSerializables();
        new TierFour().addToSerializables();
        new TierFive().addToSerializables();
    }
}
