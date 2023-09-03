package com.unibell.uni_test.repository;

import com.unibell.uni_test.UniTestApplication;
import com.unibell.uni_test.model.Client;
import com.unibell.uni_test.model.Email;
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
class EmailRepositoryTest {
    @Autowired
    private EmailRepository emailRepository;

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

        var email1 = new Email();
        email1.setEmail("user@rambler.com");
        email1.setClient(client1);

        var email2 = new Email();
        email2.setEmail("user2@gmail.com");
        email2.setClient(client2);

        emailRepository.save(email1);
        emailRepository.save(email2);

        assertEquals(email1, emailRepository.findByClientId(client1.getId()).get(0));
        assertEquals(email2, emailRepository.findByClientId(client2.getId()).get(0));

        emailRepository.deleteAll();
        clientRepository.deleteAll();
    }
}