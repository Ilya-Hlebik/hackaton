package com.web.assistant.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("BlackList")
@AllArgsConstructor
@Data
public class BlackList {
    private String id;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;
}
