package com.thepracticaldeveloper.comparison;

import java.util.concurrent.CompletableFuture;

import com.thepracticaldeveloper.objects.Actions;
import com.thepracticaldeveloper.objects.Loot;
import com.thepracticaldeveloper.objects.Thief;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureOpenSafeLock {

  private static final Logger log = LoggerFactory.getLogger(CompletableFutureOpenSafeLock.class);

  public Loot openSafeLock(final Thief thief, final String victim) {
    return CompletableFuture.supplyAsync(Actions::unlockTheDoor)
      .thenCompose(isOpened ->
        CompletableFuture.supplyAsync(() -> Actions.figureOutSafetyBoxNumber(victim))
          .thenCombineAsync(
            CompletableFuture.supplyAsync(() -> Actions.hackSecretPin(victim)),
            Actions::openSafeLock
          ).exceptionally(e -> {
            log.error("Something went wrong: {} Run, run, run!!", e.getMessage());
            return Loot.BAD;
          }
        )
      ).thenApply(
        loot -> {
          log.info("{} gets the content of the safety box: '{}'", thief.getName(), thief.handleLoot(loot));
          return loot;
        }
      ).join();
  }
}
