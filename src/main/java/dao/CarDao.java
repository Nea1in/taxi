package dao;

import java.util.List;

import entity.Cars;

public interface CarDao {

	List<Cars> getAllCars();
	boolean updateCar(int categoryId,int limit);
}
