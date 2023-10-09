package doyu.cocomo.alarm;

import doyu.cocomo.alarm.dto.AlarmCreateRequest;
import doyu.cocomo.alarm.dto.AlarmDataResponse;
import doyu.cocomo.alarm.dto.DiscordAlarmRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private AlarmService alarmService;

    @GetMapping("/list")
    public List<AlarmDataResponse> getMemberAlarms(HttpServletRequest request) {
        String cocomoKey = (String)request.getSession().getAttribute("key");
        log.info("getMemberAlarms cocomokey={}", cocomoKey);
        List<AlarmDataResponse> alarms = alarmService.getMemberAlarms(cocomoKey);
        return alarms;
    }

    @PostMapping
    public ResponseEntity<String> createAlarm(@RequestBody AlarmCreateRequest alarmCreateRequest) {
        if (alarmCreateRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        boolean isSuccess = alarmService.createAlarm(alarmCreateRequest);
        if (!isSuccess) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok("createAlarm");
    }

    @DeleteMapping("/{alarmId}")
    public ResponseEntity<String> deleteAlarm(@PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.ok("deleteAlarm");
    }

    @GetMapping("/discord")
    public ResponseEntity<String> alarmDiscordBotTest(@RequestBody DiscordAlarmRequest discordAlarmRequest) {
        alarmService.requestAlarmToDiscordBot(discordAlarmRequest);
        return ResponseEntity.ok("test");
    }
}
