package com.github.jordannegreiros.batchpoc.reader;

import com.github.jordannegreiros.batchpoc.domain.ResponseUser;
import com.github.jordannegreiros.batchpoc.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserReader implements ItemReader<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReader.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private int page = 1;
    private List<User> users = new ArrayList<>();
    private int userIndex = 0;

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        User user;

        if (userIndex < users.size())
            user = users.get(userIndex);
        else
            user = null;

        userIndex++;
        return user;
    }

    private List<User> getUser() {
        var url = String.format("https://gorest.co.in/public/v1/users?page=%d", page);
        ResponseEntity<ResponseUser> response = restTemplate.getForEntity(url, ResponseUser.class);

//        if (page == 51) {
//            throw new RuntimeException();
//        }

        LOGGER.info("Call API: {} Response: {}", url, response.getBody());
        return response.getBody().getData();
    }

    @BeforeChunk
    private void beforeChunk(ChunkContext context) {
        List<User> usersData = getUser();
        users.addAll(usersData);
        page += 50;
    }

    @AfterChunk
    private void afterChunk(ChunkContext context) {
        if (users.isEmpty())
            page = 1;

        LOGGER.info("Fim do chuck");
        users = new ArrayList<>();
        userIndex = 0;
    }
}
