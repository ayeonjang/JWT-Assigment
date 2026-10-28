package edu.classroom.board;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Value("${app.jwt.secret:}")
  private String jwtSecret;

  @Value("${app.jwt.ttl-seconds:90}")
  private long ttlSeconds;

  @Value("${app.jwt.issuer:board-app}")
  private String issuer;

  @Value("${app.jwt.audience:board-users}")
  private String audience;

  private final Users users;
  private SecretKey signingKey;

  public AuthService(Users users) {
    this.users = users;
  }

  @PostConstruct
  public void init() {
    if (jwtSecret == null || jwtSecret.isBlank()) {
      throw new IllegalArgumentException("JWT_SECRET이 설정되지 않았습니다.");
    }
    String trimmedSecret = jwtSecret.trim();
    if (trimmedSecret.length() != 64 || !trimmedSecret.matches("^[0-9a-fA-F]{64}$")) {
      throw new IllegalArgumentException("JWT_SECRET은 64자리 16진수 문자열이어야 합니다.");
    }
    if (trimmedSecret.equalsIgnoreCase("0000000000000000000000000000000000000000000000000000000000000000") ||
            trimmedSecret.equalsIgnoreCase("1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef")) {
      throw new IllegalArgumentException("제공된 예시 비밀키는 사용할 수 없습니다.");
    }

    byte[] keyBytes = new byte[32];
    for (int i = 0; i < 64; i += 2) {
      keyBytes[i / 2] = (byte) ((Character.digit(trimmedSecret.charAt(i), 16) << 4)
              + Character.digit(trimmedSecret.charAt(i + 1), 16));
    }
    this.signingKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public String issueToken(Users.PublicUser user) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + (ttlSeconds * 1000));

    return Jwts.builder()
            .subject(String.valueOf(user.id()))
            .issuer(issuer)
            .audience().add(audience).and()
            .issuedAt(now)
            .expiration(validity)
            .signWith(signingKey)
            .compact();
  }

  public long currentUserId(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new ApiError(401, "TOKEN_REQUIRED", "Authorization 헤더에 Bearer 토큰이 필요합니다.");
    }

    String token = authHeader.substring(7).trim();
    if (token.isEmpty()) {
      throw new ApiError(401, "TOKEN_REQUIRED", "토큰이 비어있습니다.");
    }

    try {
      Claims claims = Jwts.parser()
              .verifyWith(signingKey)
              .requireIssuer(issuer)
              .requireAudience(audience)
              .build()
              .parseSignedClaims(token)
              .getPayload();

      String sub = claims.getSubject();
      if (sub == null || sub.isBlank()) {
        throw new ApiError(401, "INVALID_TOKEN", "유효하지 않은 토큰 클레임입니다.");
      }

      long userId = Long.parseLong(sub);

      Users.PublicUser user = users.find(userId);
      if (user == null) {
        throw new ApiError(401, "INVALID_TOKEN", "존재하지 않는 사용자입니다.");
      }

      return userId;

    } catch (ExpiredJwtException e) {
      throw new ApiError(401, "TOKEN_EXPIRED", "토큰이 만료되었습니다.");
    } catch (JwtException | NumberFormatException e) {
      throw new ApiError(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다.");
    }
  }

  public void assertOwner(long userId, long authorId) {
    if (userId != authorId) {
      throw new ApiError(403, "FORBIDDEN", "본인이 작성한 글만 수정/삭제할 수 있습니다.");
    }
  }
}