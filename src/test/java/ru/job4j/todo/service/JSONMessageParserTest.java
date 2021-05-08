package ru.job4j.todo.service;

import org.junit.Test;
import ru.job4j.todo.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JSONMessageParserTest {

    private final MessageParser parser = new JSONMessageParser();
    private final Date now = new Date(System.currentTimeMillis());

    @Test
    public void whenPassJsonThenGetItemObject() {
        var message = "{description : task1, done : false}";
        var expected = new Item("task1", false);
        expected.setCreated(now);
        var actual = parser.getItem(message);
        actual.setCreated(now);
        assertThat(expected, is(actual));
    }
}