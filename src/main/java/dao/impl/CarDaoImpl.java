package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CarDao;
import entity.Cars;
import entity.ConnectionPool;
import entity.Status;

public class CarDaoImpl implements CarDao {
	private Connection connection;
	private static final String ALL_CARS = "SELECT * FROM cars";
	private static final String UPDATE_CAR = "UPDATE cars SET status = ? WHERE status = ? AND category_id = ? LIMIT ?";

	public CarDaoImpl() {
		try {

			connection = ConnectionPool.getConnection();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public boolean updateCar(int categoryId, int limit) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(UPDATE_CAR);
			ps.setString(1, "execute_order");
			ps.setString(2, "ready_to_order");
			ps.setInt(3, categoryId);
			ps.setInt(4, limit);
			if (ps.executeUpdate() != 1) {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public List<Cars> getAllCars() {
		List<Cars> cars = new ArrayList<Cars>();
		Cars car = null;
		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(ALL_CARS);
			while (resultSet.next()) {
				car = createEntity(resultSet);
				cars.add(car);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cars;
	}

	/**
	 * Creates entity from result set
	 * 
	 * @param rs
	 * @return entity
	 */
	private Cars createEntity(ResultSet rs) {
		Cars car = new Cars();
		try {
			car.setId(rs.getInt(1));
			car.setCategoryId(rs.getInt(2));
			car.setStatus(Status.valueOf(rs.getString(3)));
		} catch (SQLException e) {
			// LOGGER.error("SQL exception1 " + e.getMessage());
			e.printStackTrace();
		}
		return car;
	}
}
