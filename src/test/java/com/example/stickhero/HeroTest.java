package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    public void testTranslateTransition() {
        Rectangle rectangle = new Rectangle();
        TranslateTransition transition = Hero.makeTranslateTransition(rectangle, 10);
        assertNotNull(transition);
    }

//    @Test
//    public void testInitializeHero() {
//        Rectangle body = new Rectangle(5, 10);
//        Rectangle frontleg = new Rectangle(2, 5);
//        Rectangle backleg = new Rectangle(2, 5);
//        Rectangle band = new Rectangle(5, 2);
//        Circle eyes = new Circle(5);
//
//        Hero.initializeHero(band, backleg, frontleg, body, eyes);
//        Hero.setIsFlipped(true);
//        Hero.flipHero();
//        assertFalse(Hero.isFlipped());
//    }
}