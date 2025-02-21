package tacos.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import tacos.Ingredient;

public class RawJdbcIngredientRepository implements IngredientRepository {

	private DataSource dataSource;

	public RawJdbcIngredientRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		List<Ingredient> ingredients = new ArrayList<>();
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			con = dataSource.getConnection();
			statement = con.prepareStatement("select id , name , type from ingredient");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ingredient cur = new Ingredient(resultSet.getString("id"), resultSet.getString("name"),
						Ingredient.Type.valueOf(resultSet.getString("type")));
				ingredients.add(cur);
			}

		} catch (SQLException e) {
			e.printStackTrace();

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		Connection con = null;
		PreparedStatement st= null;
		ResultSet rs= null;
		
		try {
			con = dataSource.getConnection();
			st = con.prepareStatement("select id , name , type from ingredient where id = ?");
			st.setString(1, id);
			rs = st.executeQuery();
			Ingredient ans = null;
			
			if (rs.next()) {
				ans = new Ingredient(rs.getString("id") , rs.getString("name") , Ingredient.Type.valueOf(rs.getString("type")));
				
			}
			return Optional.of(ans);
		}catch(SQLException e) {
			// do some work here 
			
			if (con != null) {
				
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
		return Optional.empty();
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}

}
