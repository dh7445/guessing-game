package com.example.appl;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.logging.Logger;

import spark.Session;

import com.example.model.GuessGame;


/**
 * The object to coordinate the state of the Web Application.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GameCenter {
  private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

  //
  // Constants
  //

  final static String NO_GAMES_MESSAGE = "No games has been played so far.";
  final static String ONE_GAME_MESSAGE = "One game has been played so far.";
  final static String GAMES_PLAYED_FORMAT = "There have been %d games played.";
  final static String NO_GAMES_WON = "You have not won a game, yet. But I *feel* your luck changing.";
  final static String NO_STATS_MESSAGE = "No game stats yet";
  final static String GAMES_WON_AVERAGE_SESSION = "You have won an average of %.2f%% (%d game(s)) of this session's %d games";
  final static String GAMES_WON_AVERAGE_TOTAL = "Win Average of all users: %.2f%%";
  /**
   * The user session attribute name that points to a game object.
   */
  public final static String GAME_ID = "game";

  //
  // Attributes
  //

  private int totalGames = 0;

  //
  // Public methods
  //

  /**
   * Get the {@linkplain GuessGame game} for the current user
   * (identified by a browser session).
   *
   * @param session
   *   The HTTP session
   *
   * @return
   *   A existing or new {@link GuessGame}
   */
  public GuessGame get(final Session session) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    //
    GuessGame game = session.attribute(GAME_ID);
    if (game == null) {
      // create new game
      game = new GuessGame();
      session.attribute(GAME_ID, game);
      LOG.fine("New game created: " + game);
    }
    return game;
  }

  /**
   * End the user's current {@linkplain GuessGame game}
   * and remove it from the session.
   *
   * @param session
   *   The HTTP session
   */
  public void end(Session session) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    // remove the game from the user's session
    session.removeAttribute(GAME_ID);
    // do some application-wide book-keeping
    synchronized (this) {  // protect the critical code
      totalGames++;
    }
  }

  /**
   * Get a user message about the statistics for the whole site.
   *
   * @return
   *   The message to the user about global game statistics.
   */
  public synchronized String getGameStatsMessage() {
      double avg = (GuessGame.GamesWonTotal*100.00f) / totalGames;
      if (totalGames > 1) {
          //If there is more than one game won on any session, print the win avg of all users
          return String.format(GAMES_PLAYED_FORMAT, totalGames) + String.format(GAMES_WON_AVERAGE_TOTAL, avg);
    } else if (totalGames == 1) {
      return ONE_GAME_MESSAGE;
    } else {
      return NO_GAMES_MESSAGE;
    }
  }

  public synchronized String getGameAverageMessage(int gamesWonSession) {
      double avg = (gamesWonSession*100.00f) / totalGames;
      if (totalGames > 0){
          //If the user has won at least 1 game, print his/her winning avg
          if (gamesWonSession > 0) return String.format(GAMES_WON_AVERAGE_SESSION, avg, gamesWonSession, totalGames);
          else return NO_GAMES_WON;
      }
      else return NO_STATS_MESSAGE;
  }
}
