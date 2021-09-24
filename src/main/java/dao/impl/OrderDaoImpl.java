package dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.OrderDao;
import entity.AltOrder;
import entity.Categories;
import entity.ConnectionPool;
import entity.Orders;

public class OrderDaoImpl implements OrderDao {

	private Connection connection;

	private static final String CREATE_ORDER = "insert into orders (user_id, `from`, `to`, passenger, category_id, price ,`status`) values(?,?,?,?,?,?,?)";
	private static final String ALL_ORDER = "SELECT * FROM orders";
	private static final String GET_ID = "SELECT LAST_INSERT_ID()";
	private static final String CHECK_CATEGORY = "select categories.name from categories\n"
			+ "left join cars ON categories.id = cars.category_id\n" + "where cars.status = 'ready_to_order' AND\n"
			+ "categories.id = ? AND\n" + "categories.capacity >= ?";
	private static final String UPDATE_ORDER = "UPDATE orders SET category_id = ?, status = ?, price = ? WHERE id = ?";
	private static final String GET_CARS = "select categories.id, categories.name, categories.capacity, sum(categories.capacity) as total_capacity from categories\n"
			+ "			left join cars ON categories.id = cars.category_id \n"
			+ "			where cars.status = 'ready_to_order' AND\n" + "" + "			categories.id = ?;";

	private static final String GET_CARS_ = "select categories.id, categories.name from categories\n"
			+ "			left join cars ON categories.id = cars.category_id \n"
			+ "			where cars.status = 'ready_to_order' AND\n" + "			categories.capacity >= ?\n"
			+ "            AND categories.id <> ?\n" + "			group by categories.name;";
	private static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
	private static final String ALL_ORDER_WITH_ID = "SELECT * FROM orders\n" + "			WHERE id = ?;";
	private static final String SORTED_ORDER = "SELECT * FROM orders \n" + "order by ? ?;";

	public OrderDaoImpl() {

		try {

			connection = ConnectionPool.getConnection();
		} catch (Exception e) {
			e.getMessage();
		}
	}

/*	public List<Orders> getOrdersSorted(String fild,String order) {
		List<Orders> orders = new ArrayList<Orders>();
		Orders order = null;
		PreparedStatement pr = null;
		ResultSet resultSet;
		try {
			pr = connection.prepareStatement(SORTED_ORDER);
			pr.setString(1,);
			pr.setString(2,);
			resultSet = pr.executeQuery();
			while (resultSet.next()) {
				order = createEntity(resultSet);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}*/

	public boolean createOrder(Orders entity) {

		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement(CREATE_ORDER);

			pr.setInt(1, entity.getUserId());
			pr.setString(2, entity.getFrom());
			pr.setString(3, entity.getTo());
			pr.setInt(4, entity.getPassenger());
			pr.setInt(5, entity.getCategoryId());
			pr.setBigDecimal(6, entity.getPrice());
			pr.setInt(7, entity.getStatus());
			pr.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
		return true;

	}

	public Integer getLastOrderId() {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_ID);
			int id;
			resultSet.next();
			id = resultSet.getInt(1);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Orders> getAllOrdersWithId(int Id) {
		List<Orders> orders = new ArrayList<Orders>();
		Orders order = null;
		PreparedStatement pr = null;
		ResultSet resultSet;
		try {
			pr = connection.prepareStatement(ALL_ORDER_WITH_ID);
			pr.setInt(1, Id);
			resultSet = pr.executeQuery();
			while (resultSet.next()) {
				order = createEntity(resultSet);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}

	public String checkCategoryOrder(int id, int capacity) {
		try {
			PreparedStatement pr = null;
			ResultSet rs = null;
			pr = connection.prepareStatement(CHECK_CATEGORY);
			pr.setInt(1, id);
			pr.setInt(2, capacity);
			rs = pr.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public List<AltOrder> findCar(Integer categoryId, Integer passenger) {
		List<AltOrder> orders = new ArrayList<AltOrder>();

		PreparedStatement pr = null;
		ResultSet rs = null;
		Integer count_car = null;
		try {
			pr = connection.prepareStatement(GET_CARS);
			pr.setInt(1, categoryId);
			rs = pr.executeQuery();
			rs.next();

			if (passenger < rs.getInt(4)) {
				count_car = passenger / rs.getInt(3) + ((passenger % rs.getInt(3) == 0) ? 0 : 1);
			}
			if (count_car != null) {
				AltOrder order = new AltOrder();
				order.setId(rs.getInt(1));
				order.setName(rs.getString(2));
				order.setCount(count_car);
				orders.add(order);

			}
			pr = connection.prepareStatement(GET_CARS_);
			pr.setInt(1, passenger);
			pr.setInt(2, categoryId);
			rs = pr.executeQuery();
			while (rs.next()) {
				AltOrder order = new AltOrder();
				order.setId(rs.getInt(1));
				order.setName(rs.getString(2));
				order.setCount(1);
				orders.add(order);
			}
			System.out.println(orders);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}

	public boolean updateOrder(int id, int categoryId, int status, BigDecimal price) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(UPDATE_ORDER);
			ps.setInt(1, categoryId);
			ps.setInt(2, status);
			ps.setBigDecimal(3, price);
			ps.setInt(4, id);

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

	public boolean deleteOrder(int id) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(DELETE_ORDER);
			ps.setInt(1, id);
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

	public List<Orders> getAllOrders() {
		List<Orders> orders = new ArrayList<Orders>();
		Orders order = null;
		try {
			System.out.println(connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(ALL_ORDER);
			while (resultSet.next()) {
				order = createEntity(resultSet);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}

	private Orders createEntity(ResultSet rs) {
		Orders order = new Orders();

		try {
			order.setId(rs.getInt(1));
			order.setUserId(rs.getInt(2));
			order.setFrom(rs.getString(3));
			order.setTo(rs.getString(4));
			order.setPassenger(rs.getInt(5));
			order.setCategoryId(rs.getInt(6));
			order.setDate(rs.getDate(7));
			order.setPrice(rs.getBigDecimal(8));
			order.setStatus(rs.getInt(9));
		} catch (SQLException e) {
			// LOGGER.error("SQL exception1 " + e.getMessage());
			e.printStackTrace();
		}
		return order;
	}

}
