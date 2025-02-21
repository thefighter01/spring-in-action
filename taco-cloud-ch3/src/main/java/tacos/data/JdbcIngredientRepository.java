package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{
	
	private JdbcTemplate jdbcTemplage;
	
	public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplage = jdbcTemplate;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		String sql = "select id , name , type from ingredient";
		return jdbcTemplage.query(sql, this::mapRowToIngredient);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		String sql = "select id , name , type from ingredient where id = ?";
		List<Ingredient> ans = jdbcTemplage.query(sql, this::mapRowToIngredient , id);
		return ans.isEmpty() ?Optional.empty() : Optional.of(ans.get(0));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		String sql = "insert into ingredient (id , name , type) values(? , ? , ?)";
		jdbcTemplage.update(sql , ingredient.getId()  , ingredient.getName() , ingredient.getType().toString());
		
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs , int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id") , rs.getString("name") , Ingredient.Type.valueOf(rs.getString("type")));
	}
	
	
	

}
