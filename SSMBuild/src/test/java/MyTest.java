import com.pro.pojo.Books;
import com.pro.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookServiceImpl = (BookService) context.getBean("bookServiceImpl");
        for (Books queryAllBook : bookServiceImpl.queryAllBooks()) {
            System.out.println(queryAllBook);
        }

    }

}
