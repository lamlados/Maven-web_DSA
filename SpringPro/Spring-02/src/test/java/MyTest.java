import com.pro.pojo.Student;
import com.pro.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student);
    }
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("userBean.xml");
        User user = (User) context.getBean("user2");
        System.out.println(user);
    }

}
