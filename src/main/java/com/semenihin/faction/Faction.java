package com.semenihin.faction;

import com.semenihin.factory.FactoryInventory;
import com.semenihin.robot.PartType;

import java.util.EnumMap;
import java.util.Map;

public class Faction implements Runnable {

    private static final int CAPACITY = 5;

    private final FactoryInventory inventory;
    private final Map<PartType, Integer> collected = new EnumMap<>(PartType.class);

    public Faction(FactoryInventory inventory) {
        this.inventory = inventory;
        for (PartType type : PartType.values()) {
            collected.put(type, 0);
        }
    }

    @Override
    public void run() {
        collectParts();
    }

    private void collectParts() {
        int taken = 0;

        while (taken < CAPACITY) {
            PartType needed = findNeededPartForCurrentRobot();

            if (needed == null) {
                break;
            }

            if (inventory.takeParts(needed)) {
                collected.merge(needed, 1, Integer::sum);
                taken++;
                Thread.yield();
            } else {
                break;
            }
        }

        for (PartType type : PartType.values()) {
            while (taken < CAPACITY && inventory.takeParts(type)) {
                collected.merge(type,1, Integer::sum);
                taken++;
                Thread.yield();
            }
        }
    }

    private PartType findNeededPartForCurrentRobot() {
        for (PartType type : PartType.values()) {
            if (missingForNextRobot(type) > 0) {
                return type;
            }
        }
        return null;
    }

    private int missingForNextRobot(PartType type) {
        int robotsBuilt = robotsCanBuild();

        int used = switch (type) {
            case HEAD, TORSO -> robotsBuilt;
            case HAND, FEET -> robotsBuilt * 2;
        };

        int need = switch (type) {
            case HEAD, TORSO -> 1;
            case HAND, FEET -> 2;
        };

        int available = collected.get(type) - used;

        return Math.max(0, need - available);
    }

    public int robotsCanBuild() {
        return Math.min(
                Math.min(collected.get(PartType.HEAD), collected.get(PartType.TORSO)),
                Math.min(collected.get(PartType.HAND) / 2, collected.get(PartType.FEET) / 2)
        );
    }

    public void showNightResult(){
        System.out.println("Head count: " + collected.get(PartType.HEAD));
        System.out.println("Torso count: " + collected.get(PartType.TORSO));
        System.out.println("Hand count: " + collected.get(PartType.HAND));
        System.out.println("Feet count: " + collected.get(PartType.FEET) + "\n");
    }
}