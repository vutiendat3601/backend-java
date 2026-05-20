package vn.io.vutiendat3601.imdb.controller;

import jakarta.servlet.http.HttpServletRequest;
import vn.io.vutiendat3601.imdb.model.User;

public abstract class AbstractController {
  protected static final String SESSION_ATTRIBUTE_USER = "user";

  protected User getUserFromSession(HttpServletRequest request) {
    return (User) request.getSession().getAttribute(SESSION_ATTRIBUTE_USER);
  }
}
