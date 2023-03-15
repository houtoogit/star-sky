package com.star.sky.common.utils;

import com.star.sky.common.entities.UpgradeRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Slf4j
public class DBUtil {

    private static final String INSTALL = "install";
    private static final String UPGRADE = "upgrade";
    private static final String SQL_PATH = "config/sql";
    private static final String INSTALL_SQL_PATH = "config/sql/install.sql";

    private static final String QUERY_LAST_VERSION_SQL = "SELECT app_version FROM application_upgrade_record WHERE sub_system=? ORDER BY upgrade_time DESC LIMIT 1";
    private static final String INSERT_UPGRADE_RECORD_SQL = "INSERT INTO application_upgrade_record(sub_system, app_version, upgrade_time, upgrade_type, sql_files) VALUES(?, ?, NOW(), ?, ?)";
    private static final String CREATE_UPGRADE_RECORD_SQL = "CREATE TABLE IF NOT EXISTS application_upgrade_record(id INT NOT NULL AUTO_INCREMENT, sub_system VARCHAR(32) NOT NULL, app_version VARCHAR(32) NOT NULL, upgrade_time TIMESTAMP NOT NULL, upgrade_type VARCHAR(32) NOT NULL, sql_files VARCHAR(1024), PRIMARY KEY (id))";


    public static synchronized void initDb(Connection connection, String subSystem, String currentVersion) throws SQLException, IOException {
        UpgradeRecord record = new UpgradeRecord(subSystem, currentVersion);
        DBUtil.createUpgradeRecordTableIfNotExists(connection);
        String lastVersion = DBUtil.queryLastAppVersion(connection, subSystem);
        if (StringUtils.isNotBlank(lastVersion) && currentVersion.compareTo(lastVersion) <= 0) return;
        if (StringUtils.isBlank(lastVersion)) DBUtil.installDeploy(connection, record);
        if (StringUtils.isNotBlank(lastVersion)) DBUtil.upgradeDeploy(connection, record);
        DBUtil.insertUpgradeRecord(record, connection);
    }

    private static void installDeploy(Connection connection, UpgradeRecord record) {
        record.setUpgradeType(INSTALL);
        record.setSqlFiles(INSTALL_SQL_PATH);
        DBUtil.executeSqlScriptFile(INSTALL_SQL_PATH, connection);
    }

    private static void upgradeDeploy(Connection connection, UpgradeRecord record) throws IOException {
        record.setUpgradeType(UPGRADE);
        File file = new ClassPathResource(SQL_PATH).getFile();
        File[] upgrades = file.listFiles(sql -> sql.getName().startsWith(UPGRADE));
        if (upgrades == null || upgrades.length == 0) {
            record.setSqlFiles(null);
        } else {
            String upgradeSql = Arrays.stream(upgrades).map(File::getName).max(String::compareTo).get();
            String upgradeSqlPath = SQL_PATH + File.separator + upgradeSql;
            record.setSqlFiles(upgradeSqlPath);
            DBUtil.executeSqlScriptFile(upgradeSqlPath, connection);
        }
    }

    private static void createUpgradeRecordTableIfNotExists(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_UPGRADE_RECORD_SQL)) {
            statement.execute();
        }
    }

    private static void executeSqlScriptFile(String sqlPath, Connection connection) {
        ScriptUtils.executeSqlScript(connection, getEncodedResource(sqlPath));
        log.info("exec sql script file success: {}", sqlPath);
    }

    private static void insertUpgradeRecord(UpgradeRecord upgradeRecord, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_UPGRADE_RECORD_SQL)) {
            statement.setString(1, upgradeRecord.getSubSystem());
            statement.setString(2, upgradeRecord.getAppVersion());
            statement.setString(3, upgradeRecord.getUpgradeType());
            statement.setString(4, upgradeRecord.getSqlFiles());
            statement.execute();
        }
    }

    private static String queryLastAppVersion(Connection connection, String subSystem) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_LAST_VERSION_SQL)) {
            statement.setString(1, subSystem);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getString("app_version");
            return null;
        }
    }

    private static EncodedResource getEncodedResource(String sqlPath) {
        ClassPathResource pathResource = new ClassPathResource(sqlPath);
        return new EncodedResource(pathResource, "utf-8");
    }

}
