package com.gui.mapper;

import com.gui.domain.simple.Share;
import com.gui.dto.ShareDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class ShareMapper {

    public Map<String, Share> mapToDomainMap(Map<String, ShareDto> sharesMapDto) {
        Map<String, Share> sharesMap = new HashMap<>();
        for(ShareDto shareDto: sharesMapDto.values()) {
            sharesMap.put(shareDto.getIndex(),
                   mapToDomain(shareDto));
        }
        return sharesMap;
    }

    protected Share mapToDomain(ShareDto shareDto) {
        return new Share(shareDto.getIndex(),
                BigDecimal.valueOf(shareDto.getCurrentPrice()).setScale(2, 2),
                shareDto.getServerActualization().format(ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, 16));
    }

}
