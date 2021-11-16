package com.github.saipradyu.performance.runners;

import static com.github.saipradyu.performance.utils.Utils.POOL_SIZE;
import static com.github.saipradyu.performance.utils.Utils.TEST_SIZE;

import com.github.saipradyu.performance.domain.model.Person;
import com.github.saipradyu.performance.service.PersistenceService;
import com.github.saipradyu.performance.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Order(4)
@RequiredArgsConstructor
@Slf4j
public class AsyncWrite implements CommandLineRunner {

  @NonNull
  private PersistenceService service;

  @Override
  public void run(String... args) throws Exception {
    List<List<Person>> batches = createBatches();
    long startTime = System.nanoTime();
    //List<CompletableFuture<Void>> futureList = new ArrayList<>();
    service.doAsyncBatch();
//    for (List<Person> personList: batches) {
//      futureList.add(service.doAsyncBatch(personList));
//    }
    //futureList.forEach(CompletableFuture::join);
    long endTime = System.nanoTime();
    log.info("Asynchronous Batch Write took: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
  }

  private List<List<Person>> createBatches() {
    List<Person> personList = new ArrayList<>();
    for (int i = TEST_SIZE * 2; i < TEST_SIZE * 3; i++) {
        personList.add(Utils.createRandomPerson(i));
    }
    int workUnit = TEST_SIZE / POOL_SIZE;
    return ListUtils.partition(personList, workUnit);
  }
}
