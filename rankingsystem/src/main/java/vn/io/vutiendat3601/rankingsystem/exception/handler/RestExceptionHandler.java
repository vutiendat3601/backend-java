package vn.io.vutiendat3601.rankingsystem.exception.handler;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.io.vutiendat3601.rankingsystem.dto.response.RestErrorResponse;
import vn.io.vutiendat3601.rankingsystem.exception.CommentNotFoundException;
import vn.io.vutiendat3601.rankingsystem.exception.NoPermissionException;
import vn.io.vutiendat3601.rankingsystem.exception.PostNotFoundException;
import vn.io.vutiendat3601.rankingsystem.exception.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
  private static final Map<Class<? extends RuntimeException>, HttpStatus>
      EXCEPTION_TO_HTTP_STATUS_CODE =
          Map.of(
              UserNotFoundException.class, HttpStatus.NOT_FOUND,
              PostNotFoundException.class, HttpStatus.NOT_FOUND,
              CommentNotFoundException.class, HttpStatus.NOT_FOUND,
              NoPermissionException.class, HttpStatus.FORBIDDEN);

  private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE =
      Map.of(
          UserNotFoundException.class, "USER_NOT_FOUND",
          PostNotFoundException.class, "POST_NOT_FOUND",
          CommentNotFoundException.class, "COMMENT_NOT_FOUND",
          NoPermissionException.class, "NO_PERMISSION");

  @ExceptionHandler
  public ResponseEntity<RestErrorResponse> handleRuntimeException(RuntimeException exception) {
    var httpStatus =
        EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(
            exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    var errorCode =
        EXCEPTION_TO_ERROR_CODE.getOrDefault(
            exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR.name());

    var resp = new RestErrorResponse(httpStatus, errorCode, exception.getMessage());

    return ResponseEntity.status(resp.status()).body(resp);
  }
}
