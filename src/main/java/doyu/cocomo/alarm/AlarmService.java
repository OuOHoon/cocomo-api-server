package doyu.cocomo.alarm;

import doyu.cocomo.alarm.dto.AlarmCreateRequest;
import doyu.cocomo.alarm.dto.AlarmDataResponse;
import doyu.cocomo.alarm.dto.DiscordAlarmRequest;

import java.util.List;

public interface AlarmService {

    List<AlarmDataResponse> getMemberAlarms(String cocomoKey);

    boolean createAlarm(AlarmCreateRequest alarmCreateRequest);
    void deleteAlarm(Long alarmId);

    void scheduledAlarm();

    void requestAlarmToDiscordBot(DiscordAlarmRequest discordAlarmRequest);
}
