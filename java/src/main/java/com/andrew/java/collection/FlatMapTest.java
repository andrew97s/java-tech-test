package com.andrew.java.collection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tongwenjin
 * @since 2023/8/1
 */
public class FlatMapTest {

    public static void main(String[] args) {
        ArrayList<Entity> list = new ArrayList<>();
        list.add(new Entity("andrew" , "11"));
        list.add(new Entity("andrew1" , "12"));
        list.add(new Entity("andrew2" , "13"));

        Map<String, String> map = list.stream().collect(Collectors.toMap(Entity::getName, Entity::getValue));

        map.values().forEach(System.out::println);
    }

    @Data
    @AllArgsConstructor
    static class Entity {
        private String name;

        private String value;
    }
}
