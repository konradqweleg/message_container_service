package com.example.messagecontainer.integration;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetLastMessagesWithFriendsTest extends DefaultTestConfiguration {

    private Long getTimestampValueFromString(String date) throws ParseException {
        SimpleDateFormat dateFormatterPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date messageDate = dateFormatterPattern.parse(date);
        return new Timestamp(messageDate.getTime()).getTime();
    }
    private void insertMessage(Long id_user_sender, Long id_user_receiver, String message, String dateMessage) throws ParseException {
        SimpleDateFormat dateFormatterPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date messageDate = dateFormatterPattern.parse(dateMessage);
        Timestamp date_time_message = new Timestamp(messageDate.getTime());

        databaseClient.sql("INSERT INTO message_container_schema.message (message,id_user_sender,id_user_receiver,date_time_message) VALUES ('" + message + "', " + id_user_sender + ", " + id_user_receiver + ",'"+date_time_message+"')").fetch().rowsUpdated().block();
    }

    @Test
    public void requestShouldReturnEmptyListWhenNotExistsMessages() throws URISyntaxException {

        // when
        // then
        String idUser = "1";
        System.out.println(createRequestUtil().createRequestGetLastMessagesWithFriends() + idUser);
        webTestClient.get().uri(createRequestUtil().createRequestGetLastMessagesWithFriends() + idUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);

    }

    @Test
    public void requestShouldReturnTwoLastMessageWhenExistTwoFriendsWithMessagesBetween() throws URISyntaxException, ParseException {
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;
        Long idThirdUser = 3L;

        insertMessage(idFirstUser, idSecondUser, "Hello","2022-10-10 10:00:00");
        insertMessage(idThirdUser, idFirstUser, "Hello2","2022-10-11 10:00:00");

        webTestClient.get().uri(createRequestUtil().createRequestGetLastMessagesWithFriends() + idFirstUser.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].message").isEqualTo("Hello")
                .jsonPath("$[0].idFriend").isEqualTo(2L)
                .jsonPath("$[0].idSender").isEqualTo(1L)
                .jsonPath("$[0].idReceiver").isEqualTo(2L)
                .jsonPath("$[0].dateTimeMessage").isEqualTo(getTimestampValueFromString("2022-10-10 10:00:00"))
                .jsonPath("$[1].message").isEqualTo("Hello2")
                .jsonPath("$[1].idFriend").isEqualTo(3L)
                .jsonPath("$[1].idSender").isEqualTo(3L)
                .jsonPath("$[1].idReceiver").isEqualTo(1L)
                .jsonPath("$[1].dateTimeMessage").isEqualTo(getTimestampValueFromString("2022-10-11 10:00:00"))
                .jsonPath("$.length()").isEqualTo(2);


    }

    @Test
    public void requestShouldReturnOnlyLastMessageBetweenFriends() throws URISyntaxException, ParseException {
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;


        insertMessage(idFirstUser, idSecondUser, "Hello", "2022-10-10 10:00:00");
        insertMessage(idFirstUser, idSecondUser, "Hello2", "2022-10-11 10:01:00");

        webTestClient.get().uri(createRequestUtil().createRequestGetLastMessagesWithFriends() + idFirstUser.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].message").isEqualTo("Hello2")
                .jsonPath("$[0].idFriend").isEqualTo(2L)
                .jsonPath("$[0].idSender").isEqualTo(1L)
                .jsonPath("$[0].idReceiver").isEqualTo(2L)
                .jsonPath("$[0].dateTimeMessage").isEqualTo(getTimestampValueFromString("2022-10-11 10:01:00"))
                .jsonPath("$.length()").isEqualTo(1);

    }

    @Test
    public void requestShouldReturnOnlyLastMessagesWithFriendsConnectedToUserWithRequestParameters() throws URISyntaxException, ParseException {
        Long idFirstUser = 1L;
        Long idSecondUser = 2L;
        Long idThirdUser = 3L;

        insertMessage(idFirstUser, idSecondUser, "Hello","2022-10-10 10:11:40");
        insertMessage(idSecondUser, idThirdUser, "Hello2","2024-10-13 10:07:20");

        webTestClient.get().uri(createRequestUtil().createRequestGetLastMessagesWithFriends() + idFirstUser.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].message").isEqualTo("Hello")
                .jsonPath("$[0].idFriend").isEqualTo(2L)
                .jsonPath("$[0].idSender").isEqualTo(1L)
                .jsonPath("$[0].idReceiver").isEqualTo(2L)
                .jsonPath("$[0].dateTimeMessage").isEqualTo(getTimestampValueFromString("2022-10-10 10:11:40"))
                .jsonPath("$.length()").isEqualTo(1);
    }
}
