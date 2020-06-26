package mypackege;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class Pars {
	
	public static String replace(String source){
        //String result =  source;
        Pattern p =  Pattern.compile("<script class=\"Gc\">.*<\\/script>");
        Matcher m =  p.matcher(source);
        m.find();
		return source.substring(m.start(), m.end());
    }

	public static String replace1(String source){
        String result =  source;
        Pattern p =  Pattern.compile("\\\"id\\\":\\d+,");
        Matcher m =  p.matcher(source);
        m.find();
        result = source.substring(m.start(), m.end());
		return result.substring(5, result.length()-1);
    }
	public static String replace2(String source){
        String result =  source;
        Pattern p =  Pattern.compile("\"name\":.+,\"p");
        Matcher m =  p.matcher(source);
        m.find();
        result = source.substring(m.start(), m.end());
		return result.substring(7, result.length()-3);
    }
	public static String replace3(String source){
        String result =  source;
        Pattern p =  Pattern.compile("\"price\":\"\\d+\"");
        Matcher m =  p.matcher(source);
        m.find();
        result = source.substring(m.start(), m.end());
		return result.substring(9, result.length()-1);
    }
	public static String replace4(String source){
        String result =  source;
        Pattern p =  Pattern.compile("\"brand\":.+,\"c");
        Matcher m =  p.matcher(source);
        m.find();
        result = source.substring(m.start(), m.end());
		return result.substring(9, result.length()-4);
    }
	
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(Pars.class, args);
		Database base = (Database) context.getBean("database");
		
		String query = "https://www.bookvoed.ru/book?id=6835374";
		
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(query).openConnection();
			
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setConnectTimeout(250);
			connection.setReadTimeout(1500);
			
			connection.connect();
			
			StringBuilder sb = new StringBuilder(); 
			
			if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}
				
				String output =  replace(sb.toString());
				Long id1 = Long.parseLong(replace1(output));
				String name1 = replace2(output);
				Long price1 = Long.parseLong( replace3(output));
				String autor1 = replace4(output);
				
				if((autor1 != null)&&(price1 != 0)) {
					Book mybook= new Book(id1, name1, price1, autor1);
					base.create(mybook);
				}
				
				List<Book> byPrice = base.getByPriceSpan((long)500,(long) 200);
				for(Book book :byPrice){
					System.out.println(book);
					}
			
				System.out.println(base.getByAuthor("Вознесенская Ю.Н."));
				//System.out.println(replace1(output)+replace2(output)+ replace3(output)+ replace4(output));
			}else {
				System.out.println("fail: " + connection.getResponseCode() + connection.getResponseMessage());
			}
		} catch (Throwable cause) {
			cause.printStackTrace();
		} finally {
			if (connection !=null) {
				connection.disconnect();
			}
		}
	}
}