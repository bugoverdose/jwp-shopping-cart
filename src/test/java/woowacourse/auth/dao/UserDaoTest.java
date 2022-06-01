package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.setup.DatabaseTest;
import woowacourse.auth.domain.User;

@SuppressWarnings("NonAsciiCharacters")
class UserDaoTest extends DatabaseTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final UserDao userDao;

    public UserDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        userDao = new UserDao(jdbcTemplate);
    }

    @DisplayName("findByUserName 메서드는 아이디(username)에 해당되는 사용자 데이터를 Optional로 반환")
    @Nested
    class FindByUserNameTest {

        @Test
        void 존재하는_사용자인_경우_값이_있는_Optional_반환() {
            String 유효한_아이디 = "valid_username";
            EncryptedPassword 비밀번호 = new EncryptedPassword("valid_pw");
            User 사용자 = new User(유효한_아이디, 비밀번호);
            사용자_저장(사용자);

            User actual = userDao.findByUserName(유효한_아이디).get();

            assertThat(actual).isEqualTo(사용자);
        }

        @Test
        void 존재하지_않는_사용자인_경우_값이_없는_Optional_반환() {
            String 존재하지_않는_아이디 = "non_existing";

            boolean exists = userDao.findByUserName(존재하지_않는_아이디).isPresent();

            assertThat(exists).isFalse();
        }
    }

    private void 사용자_저장(User user) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, '닉네임', 15)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);

        jdbcTemplate.update(sql, params);
    }
}
