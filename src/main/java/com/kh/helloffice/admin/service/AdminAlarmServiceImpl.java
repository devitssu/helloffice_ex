package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminAlarmDao;
import com.kh.helloffice.admin.entity.AlarmDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAlarmServiceImpl implements AdminAlarmService{

    private final AdminAlarmDao dao;

    @Override
    public List<AlarmDto> getAlarmList() throws Exception {
        List<AlarmDto> alarmList = dao.getAlarmList();
        return alarmList.stream()
                        .map(a -> formatAlarm(a))
                        .collect(Collectors.toList());
    }

    @Override
    public int addAlarm(AlarmDto alarm) throws Exception {
        return dao.addAlarm(alarm);
    }

    @Override
    public AlarmDto getAlarm(long no) throws Exception {
        return formatAlarm(dao.getAlarm(no));
    }

    @Override
    public int editAlarm(AlarmDto alarm) throws Exception {
        return dao.editAlarm(alarm);
    }

    @Override
    public List<String> getValidAlarm(long empNo) throws Exception {
        return dao.getValidAlarm(empNo);
    }

    @Override
    public int updateLogin(long empNo) throws Exception {
        return dao.updateLogin(empNo);
    }

    private AlarmDto formatAlarm(AlarmDto alarm){
        alarm.setStartTime(formatDate(alarm.getStartTime()));
        alarm.setEndTime(formatDate(alarm.getEndTime()));
        return alarm;
    }

    private String formatDate(String origin) {
        return origin.substring(0,10);
    }
}
