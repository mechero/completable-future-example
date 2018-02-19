package com.thepracticaldeveloper;

import com.thepracticaldeveloper.comparison.CompletableFutureOpenLocker;
import com.thepracticaldeveloper.comparison.FutureOpenLocker;
import com.thepracticaldeveloper.comparison.SingleThreadOpenLocker;
import com.thepracticaldeveloper.objects.Thief;
import com.thepracticaldeveloper.objects.Loot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application: runs both approaches in sequence
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {

    log.info("\n\n SINGLE THREAD ====");
    final Loot singleThreadLoot = new SingleThreadOpenLocker().openLockerFunctional(Thief.LORA, "Mr. Toomuchmoney");
    log.info("App got the loot {}", singleThreadLoot);

    log.info("\n\n PLAIN FUTURES ====");
    final Loot plainFutureLoot = new FutureOpenLocker().openLocker(Thief.WILL, "Ms. Greedy");
    log.info("App got the loot {}", plainFutureLoot);

    log.info("\n\n COMPLETABLE FUTURE ====");
    final Loot completableFutureLoot = new CompletableFutureOpenLocker().openLocker(Thief.PETE, "Sr. Carapapas");
    log.info("App got the loot {}", completableFutureLoot);

  }

}
