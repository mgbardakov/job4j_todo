package ru.job4j.todo.service;

import org.junit.Test;
import ru.job4j.todo.model.Item;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JSONMessageParserTest {

    private final MessageParser parser = new JSONMessageParser();

    @Test
    public void whenPassJsonThenGetItemObject() {
        var message = "{description : task1, done : false}";
        var expected = new Item("task1", false);
        assertThat(expected, is(parser.getItem(message)));
    }

    @Test
    public void stringifyItemList() {
        var items = List.of(new Item("task1", false),
                                      new Item("task2", true));
        var expected = "[{\"id\":0,\"description\":\"task1\",\"done\":false}," +
                "{\"id\":0,\"description\":\"task2\",\"done\":true}]";
        assertThat(expected, is(parser.stringifyItemList(items)));
    }
}