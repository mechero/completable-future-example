package com.thepracticaldeveloper.comparison;

import com.thepracticaldeveloper.objects.Actions;
import com.thepracticaldeveloper.objects.Loot;
import com.thepracticaldeveloper.objects.Thief;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureOpenLocker {

  private static final Logger log = LoggerFactory.getLogger(CompletableFutureOpenLocker.class);

  public Loot openLocker(final Thief thief, final String victim) {
    return CompletableFuture.supplyAsync(Actions::unlockTheDoor)
      .thenApply(isOpened ->
        CompletableFuture.supplyAsync(() -> Actions.figureOutLockerNumber(victim))
          .thenCombineAsync(
            CompletableFuture.supplyAsync(() -> Actions.hackSecretPin(victim)),
            Actions::openLock
          )
      ).join()
      .thenApply(
        loot -> {
          log.info("{} gets the content of the lock: '{}'", thief.getName(), thief.handleLoot(loot));
          return loot;
        }
      ).join();
  }
}
