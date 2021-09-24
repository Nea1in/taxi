package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.impl.CarDaoImpl;
import dao.impl.CategoryDaoImpl;

public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String from;
	private String to;
	private int passenger;
	private int categoryId;
	private Date date;
	private BigDecimal price;
	private int status;

	public Orders() {
		
	}
	

	public Orders(int userId, String from, String to, int passenger, int categoryId, int status) {
		super();
		this.userId = userId;
		this.from = from;
		this.to = to;
		this.passenger = passenger;
		this.categoryId = categoryId;
		this.status = status;
		this.price = Orders.generateRandomBigDecimalFromRange(
				new BigDecimal(40.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				new BigDecimal(100.00).setScale(2, BigDecimal.ROUND_HALF_UP));
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int user) {
		this.userId = user;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getPassenger() {
		return passenger;
	}

	public void setPassenger(int passenger) {
		this.passenger = passenger;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int category) {
		this.categoryId = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
		BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
		return randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + id;
		result = prime * result + passenger;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + status;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + userId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		if (categoryId != other.categoryId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id != other.id)
			return false;
		if (passenger != other.passenger)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (status != other.status)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Orders [id=" + id + ", userId=" + userId + ", from=" + from + ", to=" + to + ", passenger=" + passenger
				+ ", categoryId=" + categoryId + ", date=" + date + ", price=" + price + ", status=" + status + "]";
	}

}
