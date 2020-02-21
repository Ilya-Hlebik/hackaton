package com.web.hackaton.dbo;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("BlackList")
@Data
public class EmailConfirmation {
    private String id;
    private int confirmationCode;
    private boolean isActive;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration = 86400000L;

    public EmailConfirmation(final String id, final int confirmationCode, final boolean isActive) {
        this.id = id;
        this.confirmationCode = confirmationCode;
        this.isActive = isActive;
    }
}
