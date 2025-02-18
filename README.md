# MyChatServer - Message Service

Message Service is a backend application responsible for managing messages between users. It allows sending, retrieving, and filtering messages while ensuring seamless integration with the User Service. The service is built using Spring WebFlux for reactive programming.

## Technologies Used

- Java
- Spring WebFlux
- Reactor
- Gradle

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.0 or higher
- Running instance of **User Service**

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/konradqweleg/mychatserver.git
    cd mychatserver
    ```

2. Configure the application:  
   Update the `application.properties` file with the required database and service connection details:
    ```properties
    user.service.url=http://user-service:8082/api/v1/users
    spring.datasource.url=jdbc:postgresql://localhost:5432/message_db
    spring.datasource.username=postgres
    spring.datasource.password=root
    ```

3. Build the project:
    ```sh
    ./gradlew build
    ```

4. Run the application:
    ```sh
    ./gradlew bootRun
    ```

---

## API Endpoints

### Create a Message
**POST** `/api/v1/messages`
- **Request Body:**
    ```json
    {
      "senderId": 1,
      "receiverId": 2,
      "content": "Hello! How are you?"
    }
    ```
- **Response:** `201 CREATED`

---

### Get Last Messages with Friends
**GET** `/api/v1/messages/{userId}/friends/last-messages`
- **Path Variable:** `userId` (User ID)
- **Response:**
    ```json
    [
      {
        "senderId": 1,
        "receiverId": 2,
        "content": "Hello!",
        "timestamp": "2024-02-18T12:34:56Z"
      },
      {
        "senderId": 3,
        "receiverId": 1,
        "content": "Hey!",
        "timestamp": "2024-02-17T10:20:30Z"
      }
    ]
    ```
- **Status Codes:**
    - `200 OK` – Returns the list of messages.
    - `404 NOT FOUND` – User not found.

---

### Get Messages Between Two Users
**GET** `/api/v1/messages/{firstUserId}/friends/{friendId}/messages`
- **Path Variables:**
    - `firstUserId` – ID of the first user
    - `friendId` – ID of the second user
- **Response:**
    ```json
    [
      {
        "senderId": 1,
        "receiverId": 2,
        "content": "How are you?",
        "timestamp": "2024-02-18T12:30:00Z"
      },
      {
        "senderId": 2,
        "receiverId": 1,
        "content": "I'm good, and you?",
        "timestamp": "2024-02-18T12:31:00Z"
      }
    ]
    ```
- **Status Codes:**
    - `200 OK` – Returns the chat history.
    - `404 NOT FOUND` – No messages found.

---

### Get All Messages with Friends
**POST** `/api/v1/messages/{userId}/friends/messages`
- **Path Variable:** `userId` (User ID)
- **Request Body:**
    ```json
    {
      "userId": 1,
      "includeRead": true
    }
    ```
- **Response:**
    ```json
    [
      {
        "friendId": 2,
        "messages": [
          {
            "senderId": 1,
            "receiverId": 2,
            "content": "Hey!",
            "timestamp": "2024-02-18T12:00:00Z"
          },
          {
            "senderId": 2,
            "receiverId": 1,
            "content": "Hello!",
            "timestamp": "2024-02-18T12:05:00Z"
          }
        ]
      }
    ]
    ```
- **Status Codes:**
    - `200 OK` – Returns the list of messages.
    - `404 NOT FOUND` – No messages found.

---

## Exception Handling

Custom exception handlers are implemented to return appropriate HTTP status codes and error messages for various failure scenarios.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
