package com.robertx22.age_of_exile.database.registry;

public enum SyncTime {

    ON_LOGIN {
        @Override
        public void trySyncIfNeeded() {

        }
    },
    ON_SKILL_TREE {
        @Override
        public void trySyncIfNeeded() {

        }
        /*
        @Override
        public void trySyncIfNeeded() {
            if (!OnLogin.CLIENT_ONLY_GOT_SKILL_PACKETS) {
                Packets.sendToServer(new RequestRegistriesPacket(this));
            } else {
                if (SlashRegistry.Spells()
                    .isEmpty()) {
                    Packets.sendToServer(new RequestRegistriesPacket(this));
                }
            }
        }

         */
    }, NEVER {
        @Override
        public void trySyncIfNeeded() {

        }
    };

    public abstract void trySyncIfNeeded();
}
