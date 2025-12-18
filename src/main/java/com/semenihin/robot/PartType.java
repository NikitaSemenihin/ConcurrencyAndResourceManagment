package com.semenihin.robot;

import java.util.Random;

public enum PartType {
    HEAD,
    TORSO,
    HAND,
    FEET;

    private static final PartType[] VALUES = values();

    public static PartType getRandomPart(Random random) {
        return VALUES[random.nextInt(VALUES.length)];
    }
}
