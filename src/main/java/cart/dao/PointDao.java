package cart.dao;

import cart.dao.dto.point.PointDto;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PointDao {

    private static final RowMapper<PointDto> ROW_MAPPER = (rs, rowNum) ->
        new PointDto(
            rs.getLong("id"),
            rs.getLong("member_id"),
            rs.getInt("point")
        );

    private final JdbcTemplate jdbcTemplate;

    public PointDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<PointDto> findByMemberId(long memberId) {
        String sql = "SELECT * from point WHERE member_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, memberId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void update(PointDto point) {
        String sql = "UPDATE point SET point = ? WHERE member_id = ?";
        jdbcTemplate.update(sql, point.getPoint(), point.getMemberId());
    }
}