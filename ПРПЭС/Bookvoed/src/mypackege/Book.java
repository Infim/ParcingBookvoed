package mypackege;

public class Book {
	
	public Long ID=null;
	public String Name=null;
	public Long Price=null;
	public String Brand=null;
	
	public Book(Long id, String name, Long price, String brand) {
		this.ID = id;
		this.Name = name;
		this.Price = price;
		this.Brand = brand;
	}
	
	public Long getId() {
		return this.ID;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public Long getPrice() {
		return this.Price;
	}
	
	public String getAutor() {
		return this.Brand;
	}
	@Override
	public String toString(){
		return Name;
	}
}