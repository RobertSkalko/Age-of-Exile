package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.vanilla_mc.packets.registry.RequestRegistriesPacket;
import com.robertx22.library_of_exile.main.Packets;

public enum SyncTime {
    ON_LOGIN {
        @Override
        public void trySyncIfNeeded() {

        }
    }, ON_SKILL_TREE {
        @Override
        public void trySyncIfNeeded() {
            if (!SlashRegistry.Spells()
                .isRegistrationDone()) {
                Packets.sendToServer(new RequestRegistriesPacket(this));

            }
        }
    }, NEVER {
        @Override
        public void trySyncIfNeeded() {

        }
    };

    public abstract void trySyncIfNeeded();
}
