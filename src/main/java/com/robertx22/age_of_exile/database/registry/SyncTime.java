package com.robertx22.age_of_exile.database.registry;

public enum SyncTime {

    ON_LOGIN {
        @Override
        public void trySyncIfNeeded() {

        }
    },
    NEVER {
        @Override
        public void trySyncIfNeeded() {

        }
    };

    public abstract void trySyncIfNeeded();
}
