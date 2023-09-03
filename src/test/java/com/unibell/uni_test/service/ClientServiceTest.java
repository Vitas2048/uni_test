package com.unibell.uni_test.service;

import com.unibell.uni_test.UniTestApplication;
import com.unibell.uni_test.core.exception.ApplicationException;
import com.unibell.uni_test.core.message.ClientDto;
import com.unibell.uni_test.core.message.Contact;
import com.unibell.uni_test.model.Client;
import com.unibell.uni_test.model.Email;
import com.unibell.uni_test.model.PhoneNum;
import com.unibell.uni_test.repository.EmailRepository;
import com.unibell.uni_test.repository.PhoneNumRepository;
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
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PhoneNumRepository phoneNumRepository;

    private Client client1;

    private Client client2;

    private PhoneNum phone1;

    private PhoneNum phone2;

    private Email email1;

    private Email email2;

    @BeforeEach
    public void init() {
        client1 = new Client();
        client1.setName("user1");

        client2 = new Client();
        client2.setName("user2");

        clientService.save(client1);
        clientService.save(client2);

        email1 = new Email();
        email1.setEmail("user@rambler.com");
        email1.setClient(client1);

        email2 = new Email();
        email2.setEmail("user2@gmail.com");
        email2.setClient(client2);

        emailRepository.save(email1);
        emailRepository.save(email2);

        phone1 = new PhoneNum();
        phone1.setPhoneNum("88001234567");
        phone1.setClient(client1);

        phone2 = new PhoneNum();
        phone2.setPhoneNum("88007654321");
        phone2.setClient(client2);

        phoneNumRepository.save(phone1);
        phoneNumRepository.save(phone2);
    }

    @AfterEach
    public void teardown() {
        client1 = null;
        client2 = null;

        phone1 = null;
        phone2 = null;

        email1 = null;
        email2 = null;

        emailRepository.deleteAll();
        phoneNumRepository.deleteAll();
        clientService.deleteAll();

    }

    @Test
    public void whenGetAll() {
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        assertEquals(clients, clientService.getAll());
    }

    @Test
    public void whenSave() throws ApplicationException {
        var client3 = new Client();
        client3.setName("test");
        clientService.save(client3);

        assertEquals(client3, clientService.findById(client3.getId()));
    }

    @Test
    public void whenGetAllClients() {
        var client2Dto = clientService.getClientDtoByClient(client2);
        var client1Dto = clientService.getClientDtoByClient(client1);

        var client1DtoRes = clientService.getAllClientsDto().get(0);
        var client2DtoRes = clientService.getAllClientsDto().get(1);


        assertEquals(client1Dto.getEmails(), client1DtoRes.getEmails());
        assertEquals(client1Dto.getNumbers(), client1DtoRes.getNumbers());
        assertEquals(client1Dto.getName(), client1DtoRes.getName());

        assertEquals(client2Dto.getEmails(), client2DtoRes.getEmails());
        assertEquals(client2Dto.getNumbers(), client2DtoRes.getNumbers());
        assertEquals(client2Dto.getName(), client2DtoRes.getName());
    }

    @Test
    public void whenFindByRightNameThenReturnUser1() throws ApplicationException {
        assertEquals(client1, clientService.findByName("user1"));
    }

    @Test
    public void whenFindByWrongThenReturnException() {
        assertThrows(ApplicationException.class, () -> {
            clientService.findByName("exception");
        });
    }

    @Test
    public void whenFindByRightIdThenReturnUser1() throws ApplicationException {
        assertEquals(client1, clientService.findById(client1.getId()));
    }

    @Test
    public void whenFindByWrongIdThenReturnException() {
        assertThrows(ApplicationException.class, () -> {
            clientService.findById(15);
        });
    }

    @Test
    public void whenGetClientDtoByClientThenReturnDto() {
        var clientDto = new ClientDto();
        clientDto.setId(client1.getId());
        clientDto.getEmails().add(email1.getEmail());
        clientDto.setName(client1.getName());
        clientDto.getNumbers().add(phone1.getPhoneNum());

        var res = clientService.getClientDtoByClient(client1);
        assertEquals(clientDto.getName(), res.getName());
        assertEquals(clientDto.getEmails(), res.getEmails());
        assertEquals(clientDto.getNumbers(), res.getNumbers());
    }

    @Test
    public void whenGetContactByNameThenReturnContact() throws ApplicationException{
        var contact = new Contact();
        contact.getEmails().add(email1.getEmail());
        contact.getNumbers().add(phone1.getPhoneNum());

        var res = clientService.getContactByName("user1");

        assertEquals(res.getEmails(), contact.getEmails());
        assertEquals(res.getNumbers(), contact.getNumbers());
    }

    @Test
    public void whenGetContactByWrongNameThenThrowException() {
        assertThrows(ApplicationException.class, () -> {
            clientService.getContactByName("exception");
        });
    }

    @Test
    public void whenGetContactByTypeEmail() throws ApplicationException {
        assertEquals(email1.getEmail(), clientService.getContactByType("user1", "email").get(0));
    }

    @Test
    public void whenGetContactByTypePhone() throws ApplicationException {
        assertEquals(phone2.getPhoneNum(), clientService.getContactByType("user2", "phone").get(0));
    }

    @Test
    public void whenGetWrongContactByTypeThenThrowException() {
        assertThrows(ApplicationException.class, () -> {
            clientService.getContactByType("Wrong", "email");
        });
    }

    @Test
    public void whenGetContactByWrongTypeThenThrowException() {
        assertThrows(ApplicationException.class, () -> {
            clientService.getContactByType("user1", "telephone");
        });
    }

    @Test
    public void whenSaveByClientDto() {
        var client3 = new Client();
        client3.setName("name");

        var clientDto = new ClientDto();
        clientDto.setId(client3.getId());
        clientDto.getEmails().add(email1.getEmail());
        clientDto.setName(client3.getName());
        clientDto.getNumbers().add(phone1.getPhoneNum());

        assertEquals(client3.getName(), clientService.saveByClientDto(clientDto).getName());
    }

    @Test
    public void whenAddNewContacts() throws ApplicationException {
        var clientDto = new ClientDto();
        clientDto.setId(client1.getId());
        var email = "cas@ad.com";
        var phone = "88809999999";
        clientDto.getEmails().add(email);
        clientDto.setName(client1.getName());
        clientDto.getNumbers().add(phone);

        clientService.addNewContacts(clientDto, client1);

        var user1 = clientService.getContactByName("user1");

        assertEquals(user1.getEmails().get(1), email);
        assertEquals(user1.getNumbers().get(1), phone);
    }
}