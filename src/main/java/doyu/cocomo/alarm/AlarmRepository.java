package doyu.cocomo.alarm;

import doyu.cocomo.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> getAlarmsByMemberId(Long memberId);
}
