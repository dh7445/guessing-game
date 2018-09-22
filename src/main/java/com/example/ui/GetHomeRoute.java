package com.example.ui;

import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.appl.GameCenter;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

/**
 * The {@code GET /} route handler; aka the Home page.
 * This is the page where the user starts (no Game yet)
 * but is also the landing page after a game ends.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements TemplateViewRoute {

  ////
  //// Put all strings into constants so that you don't duplicate
  //// these strings in your unit tests.
  ////

  static final String TITLE_ATTR = "title";
  static final String GAME_STATS_MSG_ATTR = "gameStatsMessage";
  static final String NEW_SESSION_ATTR = "newSession";
  static final String TITLE = "Welcome to the Guessing Game";
  static final String VIEW_NAME = "home.ftl";

  //
  // Attributes
  //

  private final GameCenter gameCenter;

  //
  // Constructor
  //

  /**
   * The constructor for the {@code POST /guess} route handler.
   *
   * @param gameCenter
   *    The {@link GameCenter} for the application.
   */
  GetHomeRoute(final GameCenter gameCenter) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    //
    this.gameCenter = gameCenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ModelAndView handle(Request request, Response response) {
    // retrieve the HTTP session
    final Session httpSession = request.session();

    // start building the View-Model
    final Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    // report application-wide game statistics
    vm.put(GAME_STATS_MSG_ATTR, gameCenter.getGameStatsMessage());

    // if this is a brand new browser session
    if (httpSession.isNew()) {
      // render the Game Form view
      vm.put(NEW_SESSION_ATTR, true);
      return new ModelAndView(vm, VIEW_NAME);
    }
    else {
      // there is a game already being played
      // so redirect the user to the Game view
      response.redirect(WebServer.GAME_URL);
      halt();
      return null;
    }
  }

}
