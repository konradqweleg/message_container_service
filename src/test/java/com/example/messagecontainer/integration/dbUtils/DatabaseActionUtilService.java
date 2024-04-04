package com.example.messagecontainer.integration.dbUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

@Service
public class DatabaseActionUtilService {
    @Autowired
    private DatabaseClient databaseClient;

    public void clearAllMessagesInDatabase() {
        databaseClient.sql("DELETE FROM message_container_schema.message where 1=1").
                fetch().
                rowsUpdated()
                .block();
    }

    public void clearAllDataInDatabase() {
        clearAllMessagesInDatabase();
    }
}


