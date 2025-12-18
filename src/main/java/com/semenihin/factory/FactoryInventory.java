package com.semenihin.factory;

import com.semenihin.robot.PartType;

import java.util.EnumMap;
import java.util.Map;

public class FactoryInventory {
    private final Map<PartType, Integer> parts = new EnumMap<>(PartType.class);


    public FactoryInventory(){
        for (PartType type : PartType.values()) {
            parts.put(type, 0);
        }
    }

    public synchronized void addPart(PartType type) {
        parts.put(type, parts.get(type) + 1);
    }

    public synchronized boolean takeParts(PartType type) {
        int count = parts.get(type);
        if (count > 0) {
            parts.put(type, count - 1);
            return true;
        }
        return false;
    }

    public synchronized Map<PartType, Integer> snapshot() {
        return new EnumMap<>(parts);
    }
}
