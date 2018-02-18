package com.thepracticaldeveloper.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Actions {

  private static final Logger log = LoggerFactory.getLogger(Actions.class);

  private static final long DELAY_MS = 1000L;

  private Actions() {
  }

  public static boolean unlockTheDoor() {
    log.info("Forcing the door...");
    delay(2000);
    log.info("Door unlocked!");
    return true;
  }

  public static int hackSecretPin(final String personName) {
    log.info("Hacking the pin of {}", personName);
    delay();
    final int pin = (personName.hashCode() % 1000) + 1000;
    log.info("Pin hacked: {}", pin);
    return pin;
  }

  public static String figureOutLockerNumber(final String personName) {
    log.info("Figuring out the locker number of {}", personName);
    delay();
    final String lock = "A" + ThreadLocalRandom.current().nextInt(100);
    log.info("Got the locker number: {}", lock);
    return lock;
  }

  public static Loot openLock(final String lockerNumber, final int pin) {
    log.info("Opening the locker {} using the pin {}", lockerNumber, pin);
    delay();
    log.info("Locker opened!");
    return Loot.randomLoot();
  }

  private static void delay() {
    delay(DELAY_MS);
  }

  private static void delay(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
