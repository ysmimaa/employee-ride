package com.driver.ms;

import com.driver.ms.entity.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class DriverRideApplicationTest {

    Map<String, List<Driver>> driverMap;

    @BeforeEach
    void init() {
        driverMap = new TreeMap<>();
        driverMap.put("driver1", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Youssef").lastname("Smimaa").build())));
        driverMap.put("driver2", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Kamal").lastname("Smimaa").build())));
        driverMap.put("driver3", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Said").lastname("Smimaa").build())));
        driverMap.put("driver4", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Aziz").lastname("Smimaa").build())));
        driverMap.put("driver5", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Zineb").lastname("Smimaa").build())));
        driverMap.put("driver6", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Awatif").lastname("Smimaa").build())));
        driverMap.put("driver7", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Zyad").lastname("Houssni").build())));
        driverMap.put("driver8", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Layth").lastname("Smimaa").build())));
        driverMap.put("driver9", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Rayan").lastname("Smimaa").build())));
        driverMap.put("driver10", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Jihan").lastname("Bija").build())));
        driverMap.put("driver11", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Aya").lastname("Houssni").build())));
        driverMap.put("driver12", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Mohamed").lastname("Houssni").build())));
        driverMap.put("driver13", new ArrayList<>(Arrays.asList(Driver.builder().id(1L).firstname("Said").lastname("Smimaa").build())));

    }

    @Test
    void should_return_map_result_by_using_foreach_method() {

        /** Iterate through a map using java 8 **/
        //driverMap.forEach((a, b) -> System.out.println(a + " <====> " + b));

        /**Check if the key is existed**/
        /*List<Driver> foundValues = driverMap.getOrDefault("youssef", Collections.emptyList());
        System.out.println(foundValues);*/

        /**Check putIfAbsent map function **/
   /*     List<Driver> valuesRelatedToKey = driverMap.putIfAbsent("Layth", new ArrayList<>());
        System.out.println(valuesRelatedToKey);*/

        /** check GetOrDefault map function **/
        /*driverMap.getOrDefault("youssef",new ArrayList<>()).add(Driver.builder().id(22L).lastname("lasto").build());

        List<Driver> driverListRelatedToKey = driverMap.getOrDefault("youssef", new ArrayList<>());

        System.out.println(driverListRelatedToKey);
        driverListRelatedToKey.forEach(System.out::println);*/

        /**Check map remove function **/
        /*boolean remove = driverMap.remove("driver1", "test");
          System.out.println(remove);*/

        /**Check collection computeIfAbsent function **/
        /*boolean checkValueAbsent = driverMap.computeIfAbsent(
                "driver111",
                value -> new ArrayList<>()
        ).add(Driver.builder()
                .firstname("********")
                .build()
        );

        driverMap.forEach((a, b) -> System.out.println("Key : " + a + " *** " + " Value " + b));*/


        /**Check merge collection method**/
        /*Map<String, List<String>> map1 = new HashMap<>();
        map1.putIfAbsent("map1 key1", Arrays.asList("map1 value1"));
        Map<String, List<String>> map2 = new HashMap<>();
        map1.putIfAbsent("map2 key1", Arrays.asList("map2 value1"));

        map2.forEach((key, value) ->
                map1.merge(key, value,
                        (existingElement, newElement) ->
                        {
                            existingElement.addAll(newElement);
                            return existingElement;
                        }));

        map1.forEach((key, value) -> System.out.println(" *** key *** " + key + " ==== " + "*** value ***" + value));*/


        //driverMap.values().forEach(System.out::println);

        /*driverMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .replaceAll(driver -> Driver.builder().firstname(driver.getFirstname().toUpperCase()).build());*/
        //driverMap.values().forEach(System.out::println);

        /*List<String> strings = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        strings.removeIf(str->str.equals("One"));
        strings.forEach(System.out::println);
*/
        List<String> strings = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        Map<String, List<String>> mapStrings = new HashMap<>();
        mapStrings.putIfAbsent("keyOne", new ArrayList<>());
        mapStrings.get("keyOne").add("Two");

        mapStrings.computeIfAbsent("KeyTwo", key -> new ArrayList<>()).add("Two");
        mapStrings.computeIfAbsent("KeyTwo", key -> new ArrayList<>()).add("Three");

        System.out.println(mapStrings);
    }

    @Test
    void should_compare_allMatch_new_java8_operation() {

        List<Driver> collect = driverMap.values().stream()
                .flatMap(Collection::stream)
                ///.map(Driver::getHiredDate)
                .filter(hiredDate -> hiredDate.getHiredDate() != null
                        && hiredDate.getHiredDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        Assertions.assertTrue(collect.size() == 0);

    }
}