package com.example.kalah.model.enums;

import java.util.Arrays;


public enum Player {

    ONE(1, 7),

    TWO(2, 14);

    private final Integer value;

    private final Integer storagePit;

    Player(Integer player, Integer storagePit) {
        value = player;
        this.storagePit = storagePit;
    }

    /**
     * Of player.
     *
     * @param value the value
     * @return the player
     */
    public static Player of(final Integer value) {
        return Arrays.stream(Player.values())
                .filter(player -> player.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Integer getValue() {
        return value;
    }


    /**
     * Gets storage pit.
     *
     * @return the storage pit
     */
    public Integer getStoragePit() {
        return storagePit;
    }
}
