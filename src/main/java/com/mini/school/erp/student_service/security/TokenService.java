package com.mini.school.erp.student_service.security;

import com.mini.school.erp.student_service.exception.InvalidTokenException;
import com.mini.school.erp.student_service.exception.errormessages.ErrorMessages;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    private static final String PREFIX = "auth:token:";

    public void saveToken(String username, String token, long expiryMillis) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(PREFIX + username, token, expiryMillis, TimeUnit.MILLISECONDS);
    }

    public boolean validateToken(String token) {
        Claims claims = jwtUtil.extractAllClaims(token);
        String username = claims.getSubject();

        String redisToken = redisTemplate.opsForValue().get(PREFIX + username);

        if (redisToken == null) {
            throw new InvalidTokenException(ErrorMessages.REDIS_TOKEN_VALIDATE);
        }

        if (!redisToken.equals(token)) {
            throw new InvalidTokenException(ErrorMessages.TOKEN_MISMATCH);
        }

        if (jwtUtil.isTokenExpired(token)) {
            throw new InvalidTokenException(ErrorMessages.TOKEN_EXPIRED);
        }

        return true;
    }
}
