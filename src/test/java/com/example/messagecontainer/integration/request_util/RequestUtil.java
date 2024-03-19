package com.example.messagecontainer.integration.request_util;

import java.net.URI;
import java.net.URISyntaxException;

public class RequestUtil {
    private int serverPort;
    private static String prefixHttp = "http://localhost:";
    private static String prefixServicesApiV1 = "/messageService/api/v1/message";

    public RequestUtil(int serverPort) {
        this.serverPort = serverPort;
    }

    public URI createRequestInsertMessage() throws URISyntaxException {
        return new URI(prefixHttp + serverPort + prefixServicesApiV1 + "/insertMessage");
    }

    public URI createRequestGetLastMessagesWithFriends() throws URISyntaxException {
        return new URI(prefixHttp + serverPort + prefixServicesApiV1 + "/getLastMessagesWithFriendForUser/");
    }



}
