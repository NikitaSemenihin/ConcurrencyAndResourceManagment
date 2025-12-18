package com.semenihin.factory;

import com.semenihin.robot.PartType;

import java.util.Map;
import java.util.Random;

public class Factory implements Runnable {

    private final FactoryInventory inventory;
    private final Random random = new Random();

    public Factory(FactoryInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {
        createRandomNumberOfRandomParts();
    }

    private void createRandomNumberOfRandomParts() {
        int partsCount = random.nextInt(10) + 1;

        for (int i = 0; i < partsCount; i++) {
            PartType part = PartType.getRandomPart(random);
            inventory.addPart(part);
        }
    }

    public void showDayResult() {
        Map<PartType, Integer> created = inventory.snapshot();
        System.out.println("Head count: " + created.get(PartType.HEAD));
        System.out.println("Torso count: " + created.get(PartType.TORSO));
        System.out.println("Hand count: " + created.get(PartType.HAND));
        System.out.println("Feet count: " + created.get(PartType.FEET));
    }
}