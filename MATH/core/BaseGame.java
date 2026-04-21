package MATH.core;

import MATH.model.GameState;

//  OOP: INHERITANCE + ABSTRACTION
 

public abstract class BaseGame {
    protected GameState state;

    public BaseGame() {
        state = new GameState();
    }

    public abstract void start();
    public abstract void endGame(String reason);
} 