package mypackege;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;


@Component
public class Database {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Book> rowMapper = (rowStr, rowNum) -> new Book(
			 rowStr.getLong("id"),
			 rowStr.getString("name"),
			 rowStr.getLong("price"),
			 rowStr.getString("autor")
			    );
	
	 public int create(Book book) {
	        String sql = "insert into books " +
	                "(id, Name, Price, Autor) " +
	                "values(?, ?, ?, ?)";
	        return jdbcTemplate.update(sql,
	 book.getId(),
	 book.getName(),
	 book.getPrice(),
	 book.getAutor()
	        );
	 }
	 
	 public Book get(Long Id) {
		 String sql = "select 'id','Name','Price','Autor'"+"from `books` where `id` = ?";
		 return jdbcTemplate.queryForObject(sql, Book.class, Id);
	 }
	 
	 public int update(Book book) {
		 String sql = "update books from 'books' where 'id' = ? set" + "'Name' = ? 'Price' = ?" + "'Autor' = ?";
		 return jdbcTemplate.update(sql, 
				 book.getId(),
				 book.getName(),
				 book.getPrice(),
				 book.getAutor()
				 );
	 }
	 
	 public List<Book> getByPriceSpan(Long price, Long Price) {
		 String GET = "Select * from books where price<? and price>?";
		 return jdbcTemplate.query(GET, rowMapper, price,Price);
	 }
	 
	 public List<Book> getByAuthor(String autor) {
		 String GET = "Select * from books where autor = ?";
		 return jdbcTemplate.query(GET, rowMapper, autor);
		 }
}