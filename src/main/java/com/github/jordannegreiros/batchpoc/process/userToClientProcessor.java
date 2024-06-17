package com.github.jordannegreiros.batchpoc.process;

import com.github.jordannegreiros.batchpoc.domain.User;
import com.github.jordannegreiros.batchpoc.entity.ClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class userToClientProcessor implements ItemProcessor<User, ClientEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(userToClientProcessor.class);

    @Override
    public ClientEntity process(User user) throws Exception {
        LOGGER.info("Processing user {}", user);
        var client = userToClient(user);
        LOGGER.info("Processed Client {}", client);
        return client;
    }

    private ClientEntity userToClient(User user) {
        return new ClientEntity(user.getName(), user.getEmail(), "PROCESSED");
    }
}
