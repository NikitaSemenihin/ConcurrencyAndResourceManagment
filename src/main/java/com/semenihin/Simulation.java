package com.semenihin;

import com.semenihin.faction.Faction;
import com.semenihin.factory.Factory;
import com.semenihin.factory.FactoryInventory;

import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Simulation {
    private static final int DAYS = 100;
    private final ExecutorService executor;

    private final Factory factory;

    private final Faction world;
    private final Faction wednesday;
    private final Random random;


    public Simulation() {
        this.executor = Executors.newFixedThreadPool(2);
        FactoryInventory factoryInventory = new FactoryInventory();
        this.factory = new Factory(factoryInventory);
        this.world = new Faction(factoryInventory);
        this.wednesday = new Faction(factoryInventory);
        this.random = new Random();
    }

    public void simulateDays() throws ExecutionException, InterruptedException {
        for (int day = 1; day <= DAYS; day++) {
            System.out.println("=== Day " + day + " started ===\n");
            dayActivity();
            nightActivity();
            System.out.println("=== Day " + day + " finished ===\n");
        }
        executor.shutdown();

        if (world.robotsCanBuild() > wednesday.robotsCanBuild()) {
            System.out.println("=== World Faction Win! They build: " + world.robotsCanBuild() + " robots !===");
        } else if (world.robotsCanBuild() < wednesday.robotsCanBuild()) {
            System.out.println("=== Wednesday Faction Win! They build: " + world.robotsCanBuild() + " robots !===");
        } else {
            System.out.println("=== Tie! Both Faction build: " + world.robotsCanBuild() + " robots !===");
        }
    }

    private void dayActivity() throws ExecutionException, InterruptedException {
        Future<?> future = executor.submit(factory);
        future.get();

        System.out.println("=== Factory day result ===\n");
        factory.showDayResult();
    }

    private void nightActivity() throws ExecutionException, InterruptedException {
        Future<?> f1;
        Future<?> f2;
        if (random.nextBoolean()) {
            f1 = executor.submit(world);
            f2 = executor.submit(wednesday);
        } else {
            f2 = executor.submit(wednesday);
            f1 = executor.submit(world);
        }

        f1.get();
        f2.get();

        System.out.println("=== World Faction night result ===\n");
        world.showNightResult();
        System.out.println("=== Wednesday Faction night result ===\n");
        wednesday.showNightResult();
    }

}
