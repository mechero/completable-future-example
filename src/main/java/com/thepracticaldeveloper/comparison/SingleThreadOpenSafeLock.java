package com.thepracticaldeveloper.comparison;

import com.thepracticaldeveloper.objects.Actions;
import com.thepracticaldeveloper.objects.Loot;
import com.thepracticaldeveloper.objects.Thief;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class SingleThreadOpenSafeLock {

  private static final Logger log = LoggerFactory.getLogger(SingleThreadOpenSafeLock.class);

  public Loot openSafeLock(final Thief thief, final String victim) {
    Actions.unlockTheDoor();

    final String safetyBoxNumber = Actions.figureOutSafetyBoxNumber(victim);
    final int pin = Actions.hackSecretPin(victim);

    final Loot loot = Actions.openSafeLock(safetyBoxNumber, pin);
    log.info("{} gets the content of the safety box: '{}'", thief.getName(), thief.handleLoot(loot));
    return loot;
  }

  public Loot openSafeLockFunctional(final Thief thief, final String victim) {
    return Stream.of(Actions.unlockTheDoor())
      .map((ignore) -> Actions.figureOutSafetyBoxNumber(victim))
      .map((safetyBoxNumber) -> Actions.openSafeLock(safetyBoxNumber, Actions.hackSecretPin(victim)))
      .peek((loot -> log.info("{} gets the content of the safety box: '{}'", thief.getName(), thief.handleLoot(loot))))
      .findFirst().get();
  }
}
