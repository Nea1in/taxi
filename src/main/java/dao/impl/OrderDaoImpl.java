package dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.OrderDao;
import entity.AltOrder;
import entity.Categories;
import entity.ConnectionPool;
import entity.Orders;
import entity.OrdersForAdmin;



public class OrderDaoImpl implements OrderDao {

	private Connection connection;

	private static final String CREATE_ORDER = "insert into orders (user_id, `from`, `to`, passenger, category_id, price ,`status`,date) values(?,?,?,?,?,?,?,?)";
	private static final String ALL_ORDER = "SELECT * FROM orders";
	private static final String COUNT_ORDER = "SELECT COUNT(*) FROM orders";
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
	private static final String SORTED_ORDER = "select users.login,users.email,orders.from, orders.to , orders.passenger,categories.name,orders.date,orders.price,orders.status,orders.user_id from orders\n" + 
			"					left join users ON orders.user_id = users.id\n" + 
			"					join categories ON orders.category_id = categories.id\n" + 
			"						where users.role_id = 2\n";

	public OrderDaoImpl() {

		try {

			connection = ConnectionPool.getConnection();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	
	public List<OrdersForAdmin>  getOrdersAdmin(HttpServletRequest request) {
		List<OrdersForAdmin> orders = new ArrayList<OrdersForAdmin>();
		
		PreparedStatement pr = null;
		ResultSet rs;
		
		try {
			HttpSession session = request.getSession();
			
			Integer page = Integer.parseInt(session.getAttribute("page").toString());
			String sql_str = SORTED_ORDER;
			String sql_order = null;
			String sql_where = "";
			if (session.getAttribute("user_id") != null) {
				sql_where = " AND orders.user_id=" + session.getAttribute("user_id");
			}

			if (session.getAttribute("date") != null) {
				sql_where += " AND orders.date='" + session.getAttribute("date") + "'";
			}
			sql_str += sql_where;
			
			if (session.getAttribute("sort_price") != null) {
				sql_order = "orders.price " + session.getAttribute("sort_price");
		    }
		    if (session.getAttribute("sort_date") != null) {
		    	if (sql_order != null) {
		    		sql_order += ", date " + session.getAttribute("sort_date");
		    	} else {
		    		sql_order = "orders.date " + session.getAttribute("sort_date");
		    	}
		    }
		    if (sql_order != null) {
		    	sql_str += " ORDER BY " + sql_order + " ";
		    }
			sql_str += " LIMIT " + page*5 + ", 5;";
			System.out.println(sql_str);
			pr = connection.prepareStatement(sql_str);
			rs = pr.executeQuery();
			while (rs.next()) {
				OrdersForAdmin order1 = new OrdersForAdmin();
				order1.setLogin(rs.getString(1));
				order1.setEmail(rs.getString(2));
				order1.setFrom(rs.getString(3));
				order1.setTo(rs.getString(4));
				order1.setPassenger(rs.getInt(5));
				order1.setNameCategory(rs.getString(6));
				order1.setDate(rs.getDate(7));
				order1.setPrice(rs.getBigDecimal(8));
				order1.setStatus(rs.getInt(9));
				order1.setUserId(rs.getInt(10));
				orders.add(order1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orders;
	}

	public boolean createOrder(Orders entity) {
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
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
			pr.setDate(8, sqlDate);
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

	
	public int getAllOrdersCount(HttpServletRequest request) {
		int count =0;
		HttpSession session = request.getSession();
		try {
			
			Statement statement = connection.createStatement();
			String sql_str = COUNT_ORDER;
			String sql_where = null;
			if (session.getAttribute("user_id") != null) {
				sql_where = "user_id=" + session.getAttribute("user_id");
			}
			if (session.getAttribute("date") != null) {
				if (sql_where != null) {
					sql_where += " AND date=" + session.getAttribute("date");
				} else {
					sql_where += "date=" + session.getAttribute("date");
				}
			}
			if (sql_where != null) {
				sql_str += " WHERE " + sql_where;
			}
			ResultSet resultSet = statement.executeQuery(sql_str);
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
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
