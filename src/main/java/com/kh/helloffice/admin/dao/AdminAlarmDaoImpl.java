package com.kh.helloffice.admin.dao;

import com.kh.helloffice.admin.entity.AlarmDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminAlarmDaoImpl implements AdminAlarmDao{

    private final SqlSession session;

    @Override
    public List<AlarmDto> getAlarmList() throws Exception {
        return session.selectList("admin.getAlarmList");
    }

    @Override
    public int addAlarm(AlarmDto alarm) throws Exception {
        return session.insert("admin.insertAlarm", alarm);
    }

    @Override
    public AlarmDto getAlarm(long no) throws Exception {
        return session.selectOne("admin.getAlarm", no);
    }

    @Override
    public int editAlarm(AlarmDto alarm) throws Exception {
        return session.update("admin.editAlarm", alarm);
    }

    @Override
    public List<String> getValidAlarm(long empNo) throws Exception {
        return session.selectList("admin.getValidAlarms", empNo);
    }

    @Override
    public int updateLogin(long empNo) throws Exception {
        return session.update("admin.updateLogin", empNo);
    }

    @Override
    public void expire() throws Exception {
        session.update("admin.expireAlarm");
    }
}
