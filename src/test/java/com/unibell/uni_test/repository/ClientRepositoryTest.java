package com.unibell.uni_test.repository;

import com.unibell.uni_test.UniTestApplication;
import com.unibell.uni_test.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UniTestApplication.class)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client1;

    private Client client2;

    @BeforeEach
    public void init() {
        client1 = new Client();
        client1.setName("user1");

        client2 = new Client();
        client2.setName("user2");

        clientRepository.save(client1);
        clientRepository.save(client2);
    }

    @AfterEach
    public void teardown() {
        client1 = null;
        client2 = null;

        clientRepository.deleteAll();
    }

    @Test
    public void whenGetAll() {
        List<Client> clients = new ArrayList<>();

        clients.add(client1);
        clients.add(client2);

        assertEquals(clients, clientRepository.findAll());
    }

    @Test
    public void whenFindById() {

        assertEquals(client2, clientRepository.findById(client2.getId()).get());
        assertEquals(client1, clientRepository.findById(client1.getId()).get());
    }

    @Test
    public void whenFindByName() {

        assertEquals(client2, clientRepository.findByName("user2").get());
        assertEquals(client1, clientRepository.findByName("user1").get());
    }

}