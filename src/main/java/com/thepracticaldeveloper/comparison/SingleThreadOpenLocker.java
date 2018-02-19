package com.thepracticaldeveloper.comparison;

import com.thepracticaldeveloper.objects.Actions;
import com.thepracticaldeveloper.objects.Loot;
import com.thepracticaldeveloper.objects.Thief;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class SingleThreadOpenLocker {

  private static final Logger log = LoggerFactory.getLogger(SingleThreadOpenLocker.class);

  public Loot openLocker(final Thief thief, final String victim) {
    Actions.unlockTheDoor();

    final String locker = Actions.figureOutLockerNumber(victim);
    final int pin = Actions.hackSecretPin(victim);

    final Loot loot = Actions.openLock(locker, pin);
    log.info("{} gets the content of the lock: '{}'", thief.getName(), thief.handleLoot(loot));
    return loot;
  }

  public Loot openLockerFunctional(final Thief thief, final String victim) {
    return Stream.of(Actions.unlockTheDoor())
      .map((ignore) -> Actions.figureOutLockerNumber(victim))
      .map((locker) -> Actions.openLock(locker, Actions.hackSecretPin(victim)))
      .peek((loot -> log.info("{} gets the content of the lock: '{}'", thief.getName(), thief.handleLoot(loot))))
      .findFirst().get();
  }
}
