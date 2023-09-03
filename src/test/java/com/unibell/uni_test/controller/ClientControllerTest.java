package com.unibell.uni_test.controller;

import com.google.gson.Gson;
import com.unibell.uni_test.UniTestApplication;
import com.unibell.uni_test.core.exception.ApplicationException;
import com.unibell.uni_test.core.message.ClientDto;
import com.unibell.uni_test.core.message.Contact;
import com.unibell.uni_test.core.message.ErrorCode;
import com.unibell.uni_test.model.Client;
import com.unibell.uni_test.service.ClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("main")
@SpringBootTest(classes = UniTestApplication.class)
@AutoConfigureMockMvc
class ClientControllerTest {
    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    private ClientDto clientDto1;

    private Gson gson;

    @BeforeEach
    public void init() {
        clientDto1 = new ClientDto();
        clientDto1.setId(1);
        clientDto1.setName("user1");
        clientDto1.getEmails().add("email1@com.com");
        clientDto1.getNumbers().add("88006666666");

        gson = new Gson();
    }

    @AfterEach
    public void shutdown() {
        clientDto1 = null;
        gson = null;
    }

    @Test
    public void whenAddNewWithoutException() throws Exception {
        when(clientService.saveByClientDto(clientDto1)).thenReturn(clientDto1);

        mockMvc.perform(post("http://localhost:8080/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(clientDto1)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenAddNewNotValidThrowsException() throws Exception {
        var clientDto = new ClientDto();
        mockMvc.perform(post("http://localhost:8080/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(clientDto)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void whenAddContactsWithoutException() throws Exception {
        var client1 = new Client();
        when(clientService.getClientDtoByClient(client1)).thenReturn(clientDto1);
        mockMvc.perform(put("http://localhost:8080/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(clientDto1)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenAddContactsEmptyClienDtotThrowsException() throws Exception {
        var clientDto = new ClientDto();
        mockMvc.perform(put("http://localhost:8080/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(clientDto)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void whenAddContactsNotValidNumberClientDtoThrowsException() throws Exception {
        var clientDto = new ClientDto();
        clientDto.setName("sas");
        clientDto.getNumbers().add("88");
        mockMvc.perform(put("http://localhost:8080/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(clientDto)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void whenAddContactsNotValidEmailClientDtoThrowsException() throws Exception {
        var clientDto = new ClientDto();
        clientDto.setName("sas");
        clientDto.getEmails().add("asfwe");
        mockMvc.perform(put("http://localhost:8080/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(clientDto)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void whenGetContactWithoutException() throws Exception, ApplicationException {
        var contact = new Contact();
        contact.getEmails().add(clientDto1.getEmails().get(0));
        contact.getNumbers().add(clientDto1.getNumbers().get(0));
        when(clientService.getContactByName("user1")).thenReturn(contact);
        mockMvc.perform(get("http://localhost:8080/api/client/contacts?name=user1"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenGetContactWrongNameThrowsException() throws Exception, ApplicationException {
        when(clientService.getContactByName("user3"))
                .thenThrow(new ApplicationException(ErrorCode.WRONG_CLIENT_NAME_NOT_FOUND));
        mockMvc.perform(get("http://localhost:8080/api/client/contacts?name=user3"))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void whenGetByTypeContactRightNameByEmail() throws ApplicationException, Exception {
        List<String> res = clientDto1.getEmails();
        when(clientService.getContactByType("user1", "email")).thenReturn(res);
        mockMvc.perform(get("http://localhost:8080/api/client/contactType?name=user1&type=email"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenGetByTypeContactRightNameByPhone() throws ApplicationException, Exception {
        List<String> res = clientDto1.getNumbers();
        when(clientService.getContactByType("user1", "phone"))
                .thenReturn(res);
        mockMvc.perform(get("http://localhost:8080/api/client/contactType?name=user1&type=phone"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenGetByTypeContactWrongNameByPhone() throws ApplicationException, Exception {
        when(clientService.getContactByType("user1", "phone"))
                .thenThrow(new ApplicationException(ErrorCode.CLIENT_NOT_FOUND));
        mockMvc.perform(get("http://localhost:8080/api/client/contactType?name=user1&type=phone"))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void whenGetByTypeContactRightNameByWrongType() throws ApplicationException, Exception {
        when(clientService.getContactByType("user1", "phones"))
                .thenThrow(new ApplicationException(ErrorCode.WRONG_TYPE));
        mockMvc.perform(get("http://localhost:8080/api/client/contactType?name=user1&type=phones"))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void whenGetAll() throws Exception {
        List<ClientDto> dtos = new ArrayList<>();
        dtos.add(clientDto1);
        when(clientService.getAllClientsDto()).thenReturn(dtos);
        mockMvc.perform(get("http://localhost:8080/api/client"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenGetById() throws Exception, ApplicationException {
        var client = new Client();
        when(clientService.findById(1)).thenReturn(client);
        when(clientService.getClientDtoByClient(client)).thenReturn(clientDto1);
        mockMvc.perform(get("http://localhost:8080/api/client/1"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void whenGetByWrongId() throws Exception, ApplicationException {
        var client = new Client();
        when(clientService.findById(2)).thenThrow(new ApplicationException(ErrorCode.CLIENT_NOT_FOUND));
        mockMvc.perform(get("http://localhost:8080/api/client/2"))
                .andExpect(status().isNotFound()).andReturn();
    }

}