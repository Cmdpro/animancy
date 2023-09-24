package com.cmdpro.spiritmancy.api;

public interface ISpiritContainer {
    float getSouls();
    void setSouls(float amount);

    default float getMaxSouls() {
        return 50;
    }
}
