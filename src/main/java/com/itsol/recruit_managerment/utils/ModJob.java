package com.itsol.recruit_managerment.utils;

public enum ModJob {
    NEW_JOB(1), HIGHT_SALARY(2), DEAD_LINE(3);
    private int value;

    ModJob(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
