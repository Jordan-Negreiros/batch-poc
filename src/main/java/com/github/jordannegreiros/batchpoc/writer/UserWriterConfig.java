package com.github.jordannegreiros.batchpoc.writer;


import com.github.jordannegreiros.batchpoc.ClientRepository;
import com.github.jordannegreiros.batchpoc.entity.ClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserWriterConfig implements ItemWriter<ClientEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWriterConfig.class);

    private final ClientRepository clientRepository;

    public UserWriterConfig(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void write(Chunk<? extends ClientEntity> chunk) throws Exception {
        chunk.getItems().forEach(items -> LOGGER.info("Before saving clients: {}", items));
        try {
            clientRepository.saveAll(chunk.getItems());
        } catch (Exception e) {
            chunk.getItems().forEach(items -> LOGGER.error("Before saving clients: {}", items, e));
            throw e;
        }

        chunk.getItems().forEach(items -> LOGGER.info("After saving clients: {}", items));
    }
}
