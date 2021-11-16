package com.github.saipradyu.performance.runners;

import com.github.saipradyu.performance.domain.model.Person;
import com.github.saipradyu.performance.domain.rpsy.PersonRepository;
import com.github.saipradyu.performance.utils.Utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(5)
@Component
@RequiredArgsConstructor
@Slf4j
public class ReflectionTest implements CommandLineRunner {

  private final PersonRepository personRepository;

  @Override
  public void run(String... args) throws Exception {
    Person person = Utils.createRandomPerson(1001);
    Map<Method, Method> getterSetterMap = new HashMap<>();

    List<Method> getters = Arrays.stream(person.getClass().getDeclaredMethods())
        .filter(x -> StringUtils.startsWith(x.getName(), "get"))
        .filter(x -> (x.getReturnType() == String.class))
        .collect(Collectors.toList());

    List<Field> fields = Arrays.stream(person.getClass().getDeclaredFields())
        .collect(Collectors.toList());

    Map<String, Field> fieldMap = Arrays.stream(person.getClass().getDeclaredFields())
        .collect(Collectors.toMap(this::getLowerCaseName, Function.identity()));

    for (Method method : getters) {
      log.info("Method name : {} and type :{}", method.getName(), method.getReturnType());
      log.info("Method value : {}",
          person.getClass().getDeclaredMethod(method.getName()).invoke(person));
    }

    for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
      log.info("Field lower case name : {} and return type is {}", entry.getKey(),
          entry.getValue().getType());
      if (entry.getValue().getType() == String.class) {
        PropertyUtils.setProperty(person, entry.getValue().getName(), "Set By Reflection");
        PropertyUtils.getProperty(person, entry.getValue().getName());
      }
    }
  }

  public String getLowerCaseName(Field field) {
    return StringUtils.lowerCase(field.getName());
  }
}
