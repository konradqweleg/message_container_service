package com.example.messagecontainer.integration;

import com.example.messagecontainer.integration.dbUtils.DatabaseActionUtilService;
import com.example.messagecontainer.integration.request_util.RequestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.URISyntaxException;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DefaultTestConfiguration {
    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected DatabaseActionUtilService databaseActionUtilService;

    @LocalServerPort
    protected int serverPort;

    @Autowired
    DatabaseClient databaseClient;


    protected RequestUtil createRequestUtil() {
        return new RequestUtil(serverPort);
    }

    @BeforeEach
    public void clearAllDatabaseInDatabaseBeforeRunTest() {

    }
    @AfterEach
    public void clearAllDataInDatabaseAfterRunTest() {
        databaseActionUtilService.clearAllDataInDatabase();
    }

}
