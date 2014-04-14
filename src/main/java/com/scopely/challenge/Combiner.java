package com.scopely.challenge;

import com.scopely.challenge.builder.StringValueBuilderImpl;
import com.scopely.challenge.builder.ValueBuilder;

import java.util.ArrayList;
import java.util.List;

public class Combiner {

    private List<String> comboList = new ArrayList<String>();
    private List<String> holder = new ArrayList<String>();
    private final String[] valueArray;

    /**
     *
     * @param valueArray
     */
    public Combiner(final String[] valueArray) {
        if (valueArray == null) {
            throw new IllegalArgumentException("valueArray cannot be null");
        }
        this.valueArray = valueArray;
        combineValues(0);
    }

    /**
     *
     * @return
     */
    public List<String> getComboList() {
        return comboList;
    }

    /**
     *
     * @param startIndex
     */
    private void combineValues(final int startIndex) {
        for (int i = startIndex; i < valueArray.length; i++) {
            holder.add(valueArray[i]);

            ValueBuilder<String, String> builder = new StringValueBuilderImpl();
            for (String newValue : holder) {
                builder.append(newValue);
            }
            comboList.add(builder.build());

            if (i < valueArray.length) {
                combineValues(i + 1);
            }

            holder.remove(holder.size() - 1);
        }
    }

}
