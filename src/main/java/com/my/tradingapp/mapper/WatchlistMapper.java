package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.WatchlistResponse;
import com.my.tradingapp.entity.watchlist.Watchlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StockMapper.class})
public interface WatchlistMapper {

    @Mapping(source = "user.id", target = "userId")
    WatchlistResponse toResponse(Watchlist watchlist);
}
