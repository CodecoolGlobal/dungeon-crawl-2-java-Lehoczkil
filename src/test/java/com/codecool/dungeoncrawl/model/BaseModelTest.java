package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.model.BaseModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseModelTest {
    BaseModel model = new BaseModel();

    @Test
    void setIdSetsIntId() {
        model.setId(1);
        assertEquals(1, model.getId());
    }

    @Test
    void toStringReturnsCorrectFormat() {
        assertEquals("", model.toString());
    }
}
