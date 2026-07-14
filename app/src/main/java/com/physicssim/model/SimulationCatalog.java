package com.physicssim.model;

import java.util.List;

public final class SimulationCatalog {

    private SimulationCatalog() {
    }

    public static List<SimulationItem> homeItems() {
        return List.of(
                new SimulationItem("(1)", "Pendulum\nDynamics", SimulationType.PENDULUM),
                new SimulationItem("(2)", "Work, Power &\nEnergy", SimulationType.WORK_POWER_ENERGY),
                new SimulationItem("(3)", "Kinematics", SimulationType.KINEMATICS),
                new SimulationItem("(4)", "Orbital Gravity", SimulationType.GRAVITY),
                new SimulationItem("(6)", "Electricity\nCurrent electricity", SimulationType.ELECTRICITY),
                new SimulationItem("(7)", "Atomic &\nNuclear Physics", SimulationType.ATOMIC_NUCLEAR),
                new SimulationItem("(4)", "Vector\nFundamentals", SimulationType.VECTOR));
    }
}
