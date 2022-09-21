package com.kh.helloffice;

import com.kh.helloffice.admin.dao.AdminAlarmDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmScheduler {

    private final AdminAlarmDao dao;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expire() throws Exception {
        dao.expire();
    }
}
