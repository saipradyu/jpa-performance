package com.github.saipradyu.performance.runners;

import com.github.saipradyu.performance.service.PersistenceService;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(3)
@RequiredArgsConstructor
@Component
public class CacheRead implements CommandLineRunner {

  @NonNull
  private PersistenceService persistenceService;

  @Override
  public void run(String... args) throws Exception {
    persistenceService.saveToPersistenceContext();
    long readStart = System.nanoTime();
    persistenceService.readFromLevelOneCache();
    long readEnd = System.nanoTime();
    log.info("Reading from L1 cache took: " + TimeUnit.NANOSECONDS.toMillis(readEnd - readStart));
  }
}
