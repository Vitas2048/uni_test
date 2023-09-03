package com.unibell.uni_test.service;

import com.unibell.uni_test.core.exception.ApplicationException;
import com.unibell.uni_test.core.message.Contact;
import com.unibell.uni_test.core.message.ErrorCode;
import com.unibell.uni_test.model.Client;
import com.unibell.uni_test.core.message.ClientDto;
import com.unibell.uni_test.model.Email;
import com.unibell.uni_test.model.PhoneNum;
import com.unibell.uni_test.repository.ClientRepository;
import com.unibell.uni_test.repository.EmailRepository;
import com.unibell.uni_test.repository.PhoneNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final EmailRepository emailRepository;

    private final PhoneNumRepository phoneNumRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public List<ClientDto> getAllClientsDto() {
        return getAll().stream().map(this::getClientDtoByClient).toList();
    }

    public Client findByName(String name) throws ApplicationException {
        return clientRepository.findByName(name).
                orElseThrow(new ApplicationException(ErrorCode.WRONG_CLIENT_NAME_NOT_FOUND));
    }

    public Client findById(int id) throws ApplicationException {
        return clientRepository.findById(id).
                orElseThrow(new ApplicationException(ErrorCode.CLIENT_NOT_FOUND));
    }

    public ClientDto getClientDtoByClient(Client client) {
        var clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setEmails(getEmails(client));
        clientDto.setName(client.getName());
        clientDto.setNumbers(getNumbers(client));
        return clientDto;
    }

    public Contact getContactByName(String name) throws ApplicationException{
        var contact = new Contact();
        var client = findByName(name);
        contact.setEmails(getEmails(client));
        contact.setNumbers(getNumbers(client));
        return contact;
    }

    public List<String> getContactByType(String name, String type) throws ApplicationException {
        var client = findByName(name);
        if ("email".equals(type)) {
            return getEmails(client);
        }
        if ("phone".equals(type)) {
            return getNumbers(client);
        }
        throw new ApplicationException(ErrorCode.WRONG_TYPE);
    }

    public ClientDto saveByClientDto(ClientDto clientDto) {
        var client = new Client();
        client.setName(clientDto.getName());
        save(client);
        addNewContacts(clientDto, client);
        return getClientDtoByClient(client);
    }

    public Client addNewContacts(ClientDto clientDto, Client client) {
        clientDto.getEmails().forEach(p -> {
            var email = new Email();
            email.setEmail(p);
            email.setClient(client);
            emailRepository.save(email);
        });
        clientDto.getNumbers().forEach(p -> {
            var phone = new PhoneNum();
            phone.setPhoneNum(p);
            phone.setClient(client);
            phoneNumRepository.save(phone);
        });
        return client;
    }

    public void deleteAll() {
        clientRepository.deleteAll();
    }

    private List<String> getEmails(Client client) {
        return emailRepository.findByClientId(client.getId()).stream().map(Email::getEmail).toList();
    }

    private List<String> getNumbers(Client client) {
        return phoneNumRepository.findByClientId(client.getId()).stream().map(PhoneNum::getPhoneNum).toList();
    }
}
