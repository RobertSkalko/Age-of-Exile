package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.tiers.impl.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

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
