package com.example.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.appl.GameCenter;
import com.example.model.GuessGame;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The {@code GET /game} route handler.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements TemplateViewRoute {

  static final String GAME_BEGINS_ATTR = "isFirstGuess";
  static final String GUESSES_LEFT_ATTR = "guessesLeft";
  static final String TITLE = "Number Guess Game";
  static final String VIEW_NAME = "game_form.ftl";
  static final String HINT_ATTR_MSG = "hint";

  private final GameCenter gameCenter;

  /**
   * The constructor for the {@code GET /game} route handler.
   *
   * @param gameCenter
   *    The {@link GameCenter} for the application.
   */
  GetGameRoute(final GameCenter gameCenter) {
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
    // retrieve the game object
    final GuessGame game = gameCenter.get(request.session());

    // build the View-Model
    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, TITLE);
    vm.put(GAME_BEGINS_ATTR, game.isGameBeginning());
    vm.put(GUESSES_LEFT_ATTR, game.guessesLeft());
    // render the Game Form view
    return new ModelAndView(vm, VIEW_NAME);
  }

}
