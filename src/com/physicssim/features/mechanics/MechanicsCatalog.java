package com.physicssim.features.mechanics;

import java.util.List;

public final class MechanicsCatalog {

    private MechanicsCatalog() {
    }

    public static List<MechanicsToolItem> tools() {
        return List.of(
                new MechanicsToolItem("Free Fall Simulator", "Observe time, velocity, and height changes during vertical motion.", MechanicsToolType.FREE_FALL),
                new MechanicsToolItem("Projectile Motion", "Analyze launch angle, range, max height, and flight time.", MechanicsToolType.PROJECTILE_MOTION),
                new MechanicsToolItem("Momentum Calculator", "Compute momentum from mass and velocity values instantly.", MechanicsToolType.MOMENTUM_CALCULATOR),
                new MechanicsToolItem("Hooke's Law Simulator", "Explore force-extension relationships using F = kx.", MechanicsToolType.HOOKES_LAW),
                new MechanicsToolItem("Spring Extension Calculator", "Calculate how far a spring stretches under a load.", MechanicsToolType.SPRING_EXTENSION));
    }
}
