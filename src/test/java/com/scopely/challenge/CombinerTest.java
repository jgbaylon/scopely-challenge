package com.scopely.challenge;

import com.scopely.challenge.builder.StringValueBuilderImpl;
import com.scopely.challenge.builder.ValueBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CombinerTest extends AbstractTest {

    private ValueBuilder<String, String> valueBuilder;

    @Test
    public void constructor_ValueBuilderIsNull() {
        try {
            //when
            new Combiner<String, String>(null, new String[] { });
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("valueBuilder cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_ValueArrayIsNull() {
        try {
            //when
            new Combiner<String, String>(valueBuilder, null);
            fail();
        } catch (IllegalArgumentException iae) {
            //then
            assertEquals("valueArray cannot be null", iae.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_ValueArrayIsEmpty() {
        //when
        Combiner<String, String> combiner = new Combiner<String, String>(valueBuilder, new String[] { });

        //then
        assertEquals(0, combiner.getComboList().size());
    }

    @Test
    public void getComboList() {
        //given
        String[] valueArray = new String("t1|t2|t3|t4").split("\\|");
        Combiner<String, String> combiner = new Combiner<String, String>(valueBuilder, valueArray);
        String[] comboArray = {
                "t1", "t1-t2", "t1-t2-t3", "t1-t2-t3-t4", "t1-t2-t4", "t1-t3", "t1-t3-t4", "t1-t4",
                "t2", "t2-t3", "t2-t3-t4", "t2-t4", "t3", "t3-t4", "t4"
        };
        List<String> expectedCombo = Arrays.asList(comboArray);

        //when
        List<String> actualCombo = combiner.getComboList();

        //then
        assertEquals(expectedCombo.size(), actualCombo.size());
        for (String value : expectedCombo) {
            assertEquals(true, actualCombo.contains(value));
        }
        System.out.println(actualCombo);
    }

    @Before
    public void setup() {
        valueBuilder = new StringValueBuilderImpl();
    }

}
