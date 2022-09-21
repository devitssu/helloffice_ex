package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.entity.AlarmDto;

import java.util.List;

public interface AdminAlarmService {
    List<AlarmDto> getAlarmList() throws Exception;

    int addAlarm(AlarmDto alarm) throws Exception;

    AlarmDto getAlarm(long no) throws Exception;

    int editAlarm(AlarmDto alarm) throws Exception;

    List<String> getValidAlarm(long empNo) throws Exception;

    int updateLogin(long empNo) throws Exception;
}
