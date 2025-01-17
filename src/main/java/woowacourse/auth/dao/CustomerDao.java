package woowacourse.auth.dao;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.EncryptedPassword;
import woowacourse.auth.domain.user.Customer;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) ->
            new Customer(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    new EncryptedPassword(resultSet.getString("password")),
                    resultSet.getString("nickname"),
                    resultSet.getInt("age"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Customer> findByUserName(String userName) {
        final String sql = "SELECT id, username, password, nickname, age FROM customer "
                + "WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream().findAny();
    }

    public void save(Customer customer) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, :nickname, :age)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }

    public void update(Customer customer) {
        final String sql = "UPDATE customer SET password = :password, "
                + "nickname = :nickname, age = :age WHERE id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }

    public void delete(Customer customer) {
        final String sql = "DELETE FROM customer WHERE id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }
}
