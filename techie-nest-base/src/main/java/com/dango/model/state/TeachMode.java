package com.dango.model.state;

public enum TeachMode {
    RECORD("200002", "录播"),
    LIVE("200003", "直播");

    private final String code;
    private final String description;

    TeachMode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TeachMode fromCode(String code) {
        for (TeachMode mode : TeachMode.values()) {
            if (mode.getCode().equals(code)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("未知的课程模式状态: " + code);
    }
}
