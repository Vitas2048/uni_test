package com.unibell.uni_test.repository;

import com.unibell.uni_test.UniTestApplication;
import com.unibell.uni_test.model.Client;
import com.unibell.uni_test.model.PhoneNum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UniTestApplication.class)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class PhoneNumRepositoryTest {
    @Autowired
    private PhoneNumRepository phoneNumRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void whenFindByClientId() {
        var client1 = new Client();
        client1.setName("user1");

        var client2 = new Client();
        client2.setName("user2");

        clientRepository.save(client1);
        clientRepository.save(client2);

        var phone1 = new PhoneNum();
        phone1.setPhoneNum("88001234567");
        phone1.setClient(client1);

        var phone2 = new PhoneNum();
        phone2.setPhoneNum("88007654321");
        phone2.setClient(client2);

        phoneNumRepository.save(phone1);
        phoneNumRepository.save(phone2);

        assertEquals(phone1, phoneNumRepository.findByClientId(client1.getId()).get(0));
        assertEquals(phone2, phoneNumRepository.findByClientId(client2.getId()).get(0));

        phoneNumRepository.deleteAll();
        clientRepository.deleteAll();
    }
}