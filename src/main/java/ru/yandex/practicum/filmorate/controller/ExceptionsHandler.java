package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {


    //Параметры
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectParameterException(final IncorrectParameterException e) {
        return new ErrorResponse(
                String.format("Ошибка с полем \"%s\".", e.getParameter())
        );
    }


    //Валидация

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateExeption(final ValidateException e) {
        return new ErrorResponse(e.getMessage());
    }

    //не найденно

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    //Исключение
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleObjectIsNull(final ObjectIsNull e) {
        log.error("Исключение NULL");
        return new ErrorResponse(
                e.getMessage()
        );
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDurationIsNegative(final FilmDurationIsNegative e) {
        log.error("Продолжительность фильма отрицательна");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmInvalidDataRelease(final FilmInvalidDataRelease e) {
        log.error("Недопустимая дата релиза фильма");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmInvalidName(final FilmInvalidName e) {
        log.error("Недопустимое название фильма");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmInvalidUpdateId(final FilmInvalidUpdateId e) {
        log.error("Такого Id для обновления не существует");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmLengthDescriptionTooLong(final FilmLengthDescriptionTooLong e) {
        log.error("Пустое название фильма");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserAlreadyExistException(final UserAlreadyExistException e) {
        log.error("Юзер уже существует");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserEmailWithoutSymbol(final UserEmailWithoutSymbol e) {
        log.error("Пустой email");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserInvalidBirthday(final UserInvalidBirthday e) {
        log.error("Недопустимая дата рождения");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserInvalidEmail(final UserInvalidEmail e) {
        log.error("Недопустимый email");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserInvalidLogin(final UserInvalidLogin e) {
        log.error("Недопустимый логин");
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserInvalidUpdateId(final UserInvalidUpdateId e) {
        log.error("Нет такого id для обновления");
        return new ErrorResponse(
                e.getMessage()
        );
    }


}