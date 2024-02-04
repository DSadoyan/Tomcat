package com.digi;


import com.digi.model.Address;
import com.digi.model.User;
import com.digi.repository.AddressRepositoryJPAImpl;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("application-context.xml");
//        UserRepositoryJPAImpl impl = context.getBean("userRepositoryJPAImpl", UserRepositoryJPAImpl.class);
//                impl.saveUser(new User(0,"Gor","Goroyan",1980,
//                        "gg@mail.ru","Gg123456",null,null,null));

//        AddressRepositoryJPAImpl jpa = context.getBean
//                ("addressRepositoryJPAImpl", AddressRepositoryJPAImpl.class);
//        jpa.saveAddress(new Address(0,"Armenia","Yerevan","Leo",null,0));

//        Human human = context.getBean("human", Human.class);
//        System.out.println(human.name);
    }
}
