package doyu.cocomo.alarm;

import doyu.cocomo.alarm.dto.AlarmCreateRequest;
import doyu.cocomo.alarm.dto.AlarmDataResponse;
import doyu.cocomo.alarm.dto.DiscordAlarmRequest;
import doyu.cocomo.alarm.entity.Alarm;
import doyu.cocomo.alarm.entity.AlarmLog;
import doyu.cocomo.auction.AuctionService;
import doyu.cocomo.auction.model.Auction;
import doyu.cocomo.auction.model.AuctionInfo;
import doyu.cocomo.auction.model.AuctionItem;
import doyu.cocomo.auction.model.RequestAuctionItems;
import doyu.cocomo.member.MemberRepository;
import doyu.cocomo.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

    private AlarmRepository alarmRepository;
    private MemberRepository memberRepository;
    private AuctionService auctionService;
    private AlarmLogRepository alarmLogRepository;
    private StringEncryptor stringEncryptor;

    @Value(value = "${discord.alarm-url}")
    private String discordAlarmUrl;

    public AlarmServiceImpl(AlarmRepository alarmRepository, MemberRepository memberRepository,
                            AuctionService auctionService, AlarmLogRepository alarmLogRepository,
                            StringEncryptor stringEncryptor) {
        this.alarmRepository = alarmRepository;
        this.memberRepository = memberRepository;
        this.auctionService = auctionService;
        this.alarmLogRepository = alarmLogRepository;
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    public List<AlarmDataResponse> getMemberAlarms(String cocomoKey) {
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoKey);
        if (optionalMember.isEmpty()) { // todo: 임시 예외처리임. 예외처리 어떻게 할건지 나중에 구체화 해야함
            return new ArrayList<>();
        }
        Long memberId = optionalMember.get().getId();
        List<Alarm> alarms = alarmRepository.getAlarmsByMemberId(memberId);
        List<AlarmDataResponse> alarmDataResponses = new ArrayList<>();
        for (Alarm alarm : alarms) {
            alarmDataResponses.add(alarm.toResponse());
        }
        return alarmDataResponses;
    }

    @Override
    @Transactional
    public boolean createAlarm(AlarmCreateRequest alarmCreateRequest) {
        Alarm alarm = Alarm.toEntity(alarmCreateRequest);

        String cocomoKey = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoKey);
        if (optionalMember.isEmpty()) {
            return false;
        }
        Member member = optionalMember.get();
        if (member.getAlarms().size() > 10) {
            return false;
        }
        member.addAlarm(alarm);
        alarmRepository.save(alarm);

        return true;
    }

    @Override
    public void deleteAlarm(Long alarmId) {
        String cocomoKey = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Alarm alarm = alarmRepository.findById(alarmId).get();
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoKey);
        if (optionalMember.isEmpty())
            return;
        if (!optionalMember.get().equals(alarm.getMember())) {
            return;
        }
        alarmRepository.deleteById(alarmId);
    }

    // 1분마다 경매장 검색 todo: 메소드 분리 해야함
    @Override
    @Transactional
//    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void scheduledAlarm() {

        List<Alarm> alarms = alarmRepository.findAll();
        try {
            for (Alarm alarm : alarms) {
                String apiKey = stringEncryptor.decrypt(alarm.getMember().getLostarkApiKey());
                log.info("scheduledAlarm apiKey={}", apiKey);
                RequestAuctionItems searchOption = alarm.toSearchOption();
                Auction auctionItems = auctionService.findAuctionItems(apiKey, searchOption);
                List<AuctionItem> items = auctionItems.getItems();
                log.info("scheduledAlarm items.length={}", items.size());
                for (AuctionItem item : items) {
                    if (item.getAuctionInfo().getBuyPrice() > alarm.getPrice() ||
                    item.getAuctionInfo().getTradeAllowCount() < alarm.getTradeCount()) {
                        continue;
                    }
                    AuctionInfo auctionInfo = item.getAuctionInfo();
                    String itemUniqueId = item.getGradeQuality() + auctionInfo.getEndDate()
                            + auctionInfo.getBuyPrice();
                    AlarmLog findLog = alarmLogRepository.findAlarmLogByItemUniqueId(itemUniqueId);
                    if (findLog != null) {
                        continue;
                    }
                    AlarmLog alarmLog = new AlarmLog();
                    alarm.addAlarmLog(alarmLog);
                    DiscordAlarmRequest discordAlarmRequest = DiscordAlarmRequest.builder()
                            .discordId(alarm.getMember().getDiscordId())
                            .items(List.of(item))
                            .build();
                    requestAlarmToDiscordBot(discordAlarmRequest);

                    alarmLog.setItemUniqueId(itemUniqueId);
                    alarmLogRepository.save(alarmLog);
                    log.info("itemUniqueId={}", itemUniqueId);
                }
            }
        } catch (Exception e) {
            log.error("scheduledAlarm 예외 발생, 예외 내용={}", e.toString());
        }
    }

    @Override
    public void requestAlarmToDiscordBot(DiscordAlarmRequest discordAlarmRequest) {
        WebClient webClient = WebClient.builder().build();
        ResponseEntity<Void> response = webClient.post()
                .uri(discordAlarmUrl)
                .header("Content-type", "application/json")
                .bodyValue(discordAlarmRequest)
                .retrieve()
                .toBodilessEntity()
                .block();
        log.info("디스코드 봇 알람 요청의 응답코드={}", response.getStatusCode());

    }
}
