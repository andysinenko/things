package com.synenko.things.tool.entity;

public enum ToolType {
    DRILL("DRILL"),
    PERFORATOR("PERFORATOR"),
    BELT_SANDER("BELT_SANDER"),
    ANGLE_GRINDER("ANGLE_GRINDER"),
    WELDING_INVERTER("WELDING_INVERTER"),
    JIG_SAW("JIG_SAW"),
    VIBRO_GRINDING_MACHINE("VIBRO_GRINDING_MACHINE"),
    CIRCULAR_SAW("CIRCULAR_SAW")
    ;

    private final String type;
    ToolType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
