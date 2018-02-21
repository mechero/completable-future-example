package com.thepracticaldeveloper.comparison;

import com.thepracticaldeveloper.objects.Actions;
import com.thepracticaldeveloper.objects.Thief;
import com.thepracticaldeveloper.objects.Loot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureOpenSafeLock {
  private static final Logger log = LoggerFactory.getLogger(FutureOpenSafeLock.class);

  public Loot openSafeLock(final Thief thief, final String victim) throws Exception {
    final ExecutorService executorService = Executors.newFixedThreadPool(4);
    final Future<Boolean> doorUnlockFuture = executorService.submit(
      Actions::unlockTheDoor
    );
    doorUnlockFuture.get();

    final Future<String> safetyBoxNumberFuture = executorService.submit(
      () -> Actions.figureOutSafetyBoxNumber(victim)
    );
    final Future<Integer> pinRetrieverFuture = executorService.submit(
      () -> Actions.hackSecretPin(victim)
    );

    final Future<Loot> lootFuture = executorService.submit(
      () -> Actions.openSafeLock(safetyBoxNumberFuture.get(), pinRetrieverFuture.get())
    );

    executorService.shutdown();
    final Loot loot = lootFuture.get();
    log.info("{} gets the content of the safety box: '{}'", thief.getName(), thief.handleLoot(loot));

    return loot;
  }
}
