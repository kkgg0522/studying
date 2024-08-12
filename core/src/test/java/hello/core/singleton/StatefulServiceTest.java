package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService service1 = ac.getBean(StatefulService.class);
        StatefulService service2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자 10000원 주문
        service1.order("userA",10000);
        //ThreadB: B사용자 20000원 주문
        service2.order("userB",20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = service1.getPrice();
        System.out.println(price);

        Assertions.assertThat(service1.getPrice()).isEqualTo(20000);
    }

    @Configuration
    static  class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}