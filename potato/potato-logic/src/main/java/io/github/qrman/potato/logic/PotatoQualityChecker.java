package io.github.qrman.potato.logic;

import io.github.qrman.potato.model.Potato;

public class PotatoQualityChecker {

    public void checkPotato(Potato potato) {
        if (potato.getQuality() < 100) {
            throw new RottenPotatoException("This potato is rotten!!!");
        }
    }
}
