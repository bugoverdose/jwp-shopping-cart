package woowacourse.shoppingcart.dao;

import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.Customer2;

@Repository
public class CustomerDao2 {

    private static final RowMapper<Customer2> ROW_MAPPER = (resultSet, rowNum) ->
            new Customer2(resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("nickname"),
                    resultSet.getInt("age"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao2(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Customer2> findByUserName(String userName) {
        final String sql = "SELECT username, password, nickname, age FROM customer "
                + "WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream().findAny();
    }

    public Long findIdByUserName(String userName) {
        final String sql = "SELECT id FROM customer WHERE username = :username";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public Long save(Customer2 customer) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, :nickname, :age)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void updateByUsername(Customer2 customer) {
        final String sql = "UPDATE customer SET password = :password, "
                + "nickname = :nickname, age = :age WHERE username = :username";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }

    public void delete(Customer2 customer) {
        final String sql = "DELETE FROM customer WHERE username = :username";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }
}
