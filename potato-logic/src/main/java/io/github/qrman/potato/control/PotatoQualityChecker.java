package io.github.qrman.potato.control;

import io.github.qrman.potato.entity.Potato;

public class PotatoQualityChecker {

    public void check(Potato potato) {
        if (potato.getQuality() < 100) {
            throw new RottenPotatoException("This potato is rotten!!!");
        }
    }
}
