package dao;

import java.util.List;

import entity.Orders;

public interface OrderDao {
	public boolean createOrder(Orders entity);

	public List<Orders> getAllOrders();
}
