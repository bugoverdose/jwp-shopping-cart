package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.domain.Token;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationException;

@Service
public class AuthService {

    private final UserDao userDao;
    private final JwtTokenService tokenService;

    public AuthService(UserDao userDao, JwtTokenService tokenService) {
        this.userDao = userDao;
        this.tokenService = tokenService;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        Token accessToken = tokenService.generateToken(user.getTokenPayload());
        return new TokenResponse(accessToken.getValue());
    }

    private User findValidUser(String username, String password) {
        User user = findUser(username);
        if (user.hasDifferentPassword(password)) {
            throw new AuthenticationException();
        }
        return user;
    }

    public User findUserByToken(String token) {
        String username = tokenService.extractPayload(new Token(token));
        return findUser(username);
    }

    private User findUser(String username) {
        return userDao.findByUserName(username)
                .orElseThrow(AuthenticationException::new);
    }
}
