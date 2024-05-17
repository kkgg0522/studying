package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver resolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = resolver.resolveMessageCodes("required", "item");
        for(String x : messageCodes){
            System.out.println(x);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = resolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for(String x : messageCodes){
            System.out.println(x);
        }
        assertThat(messageCodes).containsExactly("required.item.itemName","required.itemName","required.java.lang.String", "required");
    }
}
