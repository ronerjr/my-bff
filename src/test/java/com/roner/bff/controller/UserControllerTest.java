package com.roner.bff.controller;

import com.roner.bff.entity.User;
import com.roner.bff.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {

    private static final String BASE_PATH = "/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsers() throws Exception {
        userRepository.save(User.builder().name("Test getUsers()").build());
        mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name", Matchers.is("Test getUsers()")));
    }

    @Test
    void getUserById() throws Exception {
        User user = userRepository.save(User.builder().name("Test getUsers()").build());
        mockMvc.perform(get(String.format("%s/%s", BASE_PATH, user.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Test getUsers()")));
    }

    @Test
    void insertUser() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                .content("{\n" +
                        "\t\"age\":10,\n" +
                        "\t\"name\": \"Thiago\",\n" +
                        "\t\"username\": \"t\",\n" +
                        "\t\"email\": \"t@email.com\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Thiago")));
    }

    @Test
    void updateUser() throws Exception {
        User user = userRepository.save(User.builder().name("Test updateUser()").build());
        mockMvc.perform(put(String.format("%s/%s", BASE_PATH, user.getId()))
                .content("{\n" +
                        "\t\"age\":10,\n" +
                        "\t\"name\": \"Thiago\",\n" +
                        "\t\"username\": \"t\",\n" +
                        "\t\"email\": \"t@email.com\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Thiago")));
    }

    @Test
    void deleteUser() throws Exception {
        User user = userRepository.save(User.builder().name("Test deleteUser()").build());
        mockMvc.perform(delete(String.format("%s/%s", BASE_PATH, user.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}