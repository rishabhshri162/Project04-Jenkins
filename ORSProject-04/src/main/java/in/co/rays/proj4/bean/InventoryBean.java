package in.co.rays.proj4.bean;

public class InventoryBean extends BaseBean {

	private String itemName;
	private String stock;
	private String price;
	private String quantity;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return itemName + " - " + stock;
	}
}
