package com.star.sky.common.beans;

import lombok.Data;

@Data
public class UpgradeRecord {

    private String subSystem;
    private String appVersion;
    private String upgradeTime;
    private String upgradeType;
    private String sqlFiles;

    public UpgradeRecord(String subSystem, String appVersion) {
        this.subSystem = subSystem;
        this.appVersion = appVersion;
    }
}
