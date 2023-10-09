package doyu.cocomo.auction;

import com.fasterxml.jackson.core.JsonProcessingException;
import doyu.cocomo.auction.model.Auction;
import doyu.cocomo.auction.model.RequestAuctionItems;

public interface AuctionService {


    public Auction findAuctionItems(String apiKey, RequestAuctionItems searchOption) throws JsonProcessingException;

}
