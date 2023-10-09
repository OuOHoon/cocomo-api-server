package doyu.cocomo.auction;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doyu.cocomo.auction.model.Auction;
import doyu.cocomo.auction.model.RequestAuctionItems;
import doyu.cocomo.member.MemberService;
import doyu.cocomo.member.dto.MemberLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/auction")
public class AuctionController {

    private AuctionService auctionService;
    private MemberService memberService;


    public AuctionController(AuctionService auctionService, MemberService memberService) {
        this.auctionService = auctionService;
        this.memberService = memberService;
    }

    @PostMapping("/search")
    public ResponseEntity<Auction> search(@RequestBody RequestAuctionItems requestAuctionItems) throws JsonProcessingException {
        String cocomoKey = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        String key = memberService.getApiKeyByCocomoKey(cocomoKey);

        Auction auctionItems = auctionService.findAuctionItems(key, requestAuctionItems);

        return ResponseEntity.ok(auctionItems);
    }
}
