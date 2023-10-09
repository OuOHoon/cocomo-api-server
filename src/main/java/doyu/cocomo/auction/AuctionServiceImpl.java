package doyu.cocomo.auction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doyu.cocomo.alarm.AlarmRepository;
import doyu.cocomo.auction.model.Auction;
import doyu.cocomo.auction.model.RequestAuctionItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class AuctionServiceImpl implements AuctionService {

    private final String auctionSearchUrl = "https://developer-lostark.game.onstove.com/auctions/items";
    private final WebClient webClient;


    public AuctionServiceImpl() {
        webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Auction findAuctionItems(String apiKey, RequestAuctionItems searchOption) throws JsonProcessingException {

        String response = webClient.post()
                .uri(auctionSearchUrl)
                .header("authorization", "bearer " + apiKey)
                .bodyValue(searchOption)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();

        Auction auction = objectMapper.readValue(response, Auction.class);
        log.info("auction={}", auction.toString());
        return auction;
    }
}
