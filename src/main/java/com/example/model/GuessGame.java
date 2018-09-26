package com.example.model;

import java.util.Random;

/**
 * A single "guessing game".
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GuessGame {

  //
  // Constants
  //

  /**
   * The upper bound on the numbers to be guessed. This is public so that other
   * components to read it; especially for unit testing.
   */

  public static final int UPPER_BOUND = 10;

  /**
   * The number of guess attempts alloted. This is public so that other
   * components to read it; especially for unit testing.
   */
  public static final int NUM_OF_GUESSES = 3;

  private static final Random RANDOM = new Random();

  public static int GamesWonTotal = 0;
  //
  // Attributes
  //

  private final int numberToGuess;
  private int howManyGuessesLeft = NUM_OF_GUESSES;

  //
  // Constructors
  //

  /**
   * Create a guessing game with a known number.
   *
   * @param numberToGuess
   *          The number to be guessed.
   *
   * @throws IllegalArgumentException
   *    when the {@code numberToGuess} is out of range
   */
  public GuessGame(final int numberToGuess) {
    // validate arguments
    if (numberToGuess < 0 || numberToGuess >= UPPER_BOUND) {
      throw new IllegalArgumentException("numberToGuess is out of range");
    }
    //
    this.numberToGuess = numberToGuess;
  }

  /**
   * Create a guessing game with a random number.
   */
  public GuessGame() {
    this(RANDOM.nextInt(UPPER_BOUND));
  }

  //
  // Public methods
  //

  /**
   * Queries whether the game is at the beginning; meaning no guesses have yet
   * been made.
   *
   * @return true if no guesses have been made, otherwise, false
   */
  public synchronized boolean isGameBeginning() {
    return howManyGuessesLeft == NUM_OF_GUESSES;
  }

  /**
   * Queries whether the supplied guess is a valid guess. This does not actually
   * issue a guess.
   *
   * @param guess
   *          The hypothetical guess.
   *
   * @return true if the guess falls within the game bounds, otherwise, false
   */
  public boolean isValidGuess(int guess) {
    return guess >= 0 && guess < UPPER_BOUND;
  }

  /**
   * Makes a guess on the game.
   *
   * @param myGuess
   *          The user's guess.
   *
   * @return true if the guess matches the hidden value, otherwise, false
   */
  public synchronized boolean makeGuess(final int myGuess) {
    // validate arguments
    if (myGuess < 0 || myGuess >= UPPER_BOUND) {
      throw new IllegalArgumentException("myGuess is out of range");
    }
    // validate that the game isn't over
    if (howManyGuessesLeft == 0) {
      throw new IllegalStateException("No more guesses allowed.");
    }
    //
    howManyGuessesLeft--;
    //if its the right guess, increase the total of games won by all users
    if (myGuess == numberToGuess) GamesWonTotal++;
    return myGuess == numberToGuess;
  }

  /**
   * Queries whether the user has more guesses for this game.
   *
   * @return true if there are more guesses to be made, otherwise, false
   */
  public synchronized boolean hasMoreGuesses() {
    return howManyGuessesLeft > 0;
  }

  /**
   * Queries for the number of guesses left in the game.
   *
   * @return the number of guesses left in this game
   */
  public synchronized int guessesLeft() {
    return howManyGuessesLeft;
  }

    /**
     * Queries if the user guess is higher or lower than the expected number.
     *
     * @return higher or lower hint
     */
  public synchronized String provideHint (int myGuess) {
    if (myGuess < numberToGuess) return "The number is higher than your guess";
    else return "The number is lower than your guess";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized String toString() {
    return "{Game " + numberToGuess + "}";
  }
}
