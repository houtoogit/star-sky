package com.star.sky.member.init;

import com.star.sky.common.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;

import static com.star.sky.common.utils.ThreadUtil.workThread;

@Slf4j
@Component
public class InitDataService {

    @Value("${app.version}")
    private String currentVersion;

    @Value("${server.servlet.context-path}")
    private String subSystem;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initData() {
        workThread.submit(this::initDb);
    }

    private void initDb() {
        try (Connection connection = dataSource.getConnection()) {
            DBUtil.initDb(connection, subSystem, currentVersion);
            log.info("init db success.");
        } catch (Exception e) {
            log.error("init db fail: {}, ", e.getMessage(), e);
        }
    }

}
