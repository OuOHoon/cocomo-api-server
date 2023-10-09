package doyu.cocomo.alarm;


import doyu.cocomo.alarm.entity.AlarmLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Long> {
    List<AlarmLog> findAlarmLogsByAlarmId(Long alarmId);

    AlarmLog findAlarmLogByItemUniqueId(String itemUniqueId);
}
