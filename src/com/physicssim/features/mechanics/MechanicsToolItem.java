package com.physicssim.features.mechanics;

public class MechanicsToolItem {

    private final String title;
    private final String description;
    private final MechanicsToolType type;

    public MechanicsToolItem(String title, String description, MechanicsToolType type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public MechanicsToolType getType() {
        return type;
    }
}
