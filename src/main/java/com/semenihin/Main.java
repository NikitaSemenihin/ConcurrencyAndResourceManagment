package com.semenihin;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        try {
            simulation.simulateDays();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
