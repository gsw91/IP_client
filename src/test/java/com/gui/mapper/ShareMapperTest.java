package com.gui.mapper;

import com.gui.domain.Share;
import com.gui.dto.ShareDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class ShareMapperTest {

    private ShareMapper mapper = new ShareMapper();

    @Test
    public void testMapToDomain() {
        //given
        ShareDto shareDto = new ShareDto("KREZUS", 0.61, LocalDateTime.parse("2018-11-18T18:39:33.488"),  LocalDateTime.parse("2018-11-16T17:02:00"));
        //when
        Share share = mapper.mapToDomain(shareDto);
        //then
        Assert.assertEquals("KREZUS", share.getIndex());
        Assert.assertEquals(BigDecimal.valueOf(0.61), share.getCurrentPrice());
        Assert.assertEquals("2018-11-18 18:39", share.getServerActualization());
    }

    @Test
    public void testmapToDomainMap() {
        //given
        Map<String, ShareDto> sharesMapDto = new HashMap<>();
        sharesMapDto.put("KREZUS", new ShareDto("KREZUS", 0.61, LocalDateTime.parse("2018-11-18T18:39:33.488"),  LocalDateTime.parse("2018-11-16T17:02:00")));
        sharesMapDto.put("POLIMEXMS", new ShareDto("POLIMEXMS", 2.87 , LocalDateTime.parse("2018-11-18T18:39:33.504"),  LocalDateTime.parse("2018-11-16T17:03:00")));
        sharesMapDto.put("PKNORLEN", new ShareDto("PKNORLEN", 99.00, LocalDateTime.parse("2018-11-18T18:39:33.504"),  LocalDateTime.parse("2018-11-16T17:04:00")));
        //when
        Map<String, Share> shareMap = mapper.mapToDomainMap(sharesMapDto);
        //then
        Assert.assertEquals(3, shareMap.size());
        Assert.assertTrue(shareMap.containsKey("KREZUS"));
        Assert.assertTrue(shareMap.containsKey("POLIMEXMS"));
        Assert.assertTrue(shareMap.containsKey("PKNORLEN"));
    }

}
