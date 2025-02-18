package com.example.messagecontainer.adapter.in.rest.exception_handlers;

import com.example.messagecontainer.adapter.in.rest.error.ErrorResponse;
import com.example.messagecontainer.adapter.in.rest.error.ErrorResponseUtil;
import com.example.messagecontainer.exception.friend_service.FriendDataParsingException;
import com.example.messagecontainer.exception.friend_service.GeneralFriendServiceException;
import com.example.messagecontainer.exception.friend_service.InvalidFriendRequestException;
import com.example.messagecontainer.exception.friend_service.UserAreNotFriendsException;
import com.example.messagecontainer.exception.repository.RepositoryException;
import com.example.messagecontainer.exception.user_service.GeneralUserServiceException;
import com.example.messagecontainer.exception.user_service.InvalidUserRequestException;
import com.example.messagecontainer.exception.user_service.UserDataParsingException;
import com.example.messagecontainer.exception.user_service.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class MessageControllerExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(FriendDataParsingException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleFriendDataParsingException(FriendDataParsingException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GeneralFriendServiceException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralFriendServiceException(GeneralFriendServiceException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFriendRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidFriendRequestException(InvalidFriendRequestException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAreNotFriendsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserAreNotFriendsException(UserAreNotFriendsException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RepositoryException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleRepositoryException(RepositoryException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GeneralUserServiceException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralUserServiceException(GeneralUserServiceException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidUserRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidUserRequestException(InvalidUserRequestException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDataParsingException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserDataParsingException(UserDataParsingException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserNotFoundException(UserNotFoundException ex, ServerWebExchange exchange) {
        return ErrorResponseUtil.generateErrorResponseEntity(ex, exchange, HttpStatus.BAD_REQUEST);
    }


}
