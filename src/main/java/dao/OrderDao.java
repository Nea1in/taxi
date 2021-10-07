package dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import entity.AltOrder;
import entity.Orders;
import entity.OrdersForAdmin;

public interface OrderDao {
	
	
	List<OrdersForAdmin>  getOrdersAdmin(HttpServletRequest request);
	public boolean createOrder(Orders entity);
	Integer getLastOrderId();
	List<Orders> getAllOrdersWithId(int Id);
	String checkCategoryOrder(int id, int capacity);
	List<AltOrder> findCar(Integer categoryId, Integer passenger);
	boolean updateOrder(int id, int categoryId, int status, BigDecimal price);
	boolean deleteOrder(int id);
	int getAllOrdersCount(HttpServletRequest request);
	public List<Orders> getAllOrders();
}
