package com.scopely.challenge.builder;

import com.scopely.challenge.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringValueBuilderImplTest extends AbstractTest {

    private ValueBuilder<String, String> valueBuilder;

    @Test
    public void build() {
        try {
            //when
            valueBuilder.build();
            fail();
        } catch (UnsupportedOperationException e) {
            //then
            assertEquals("cannot build on empty values", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void appendAndBuild() {
        //when
        valueBuilder.append("favorites").append("   ").append("default");

        //then
        assertEquals("favorites-   -default", valueBuilder.build());
    }

    @Test
    public void appendAndBuild_ValueIsNull() {
        try {
            //when
            valueBuilder.append(null).build();
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("value cannot be null or empty", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void appendAndBuild_ValueIsEmpty() {
        try {
            //when
            valueBuilder.append("").build();
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("value cannot be null or empty", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void clear() {
        //given
        valueBuilder.append("favorites").append("   ").append("default");


        //when
        valueBuilder.clear();

        //then
        try {
            valueBuilder.build();
            fail();
        } catch (UnsupportedOperationException e) {
            assertEquals("cannot build on empty values", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Before
    public void setup() {
        valueBuilder  = new StringValueBuilderImpl();
    }

}
