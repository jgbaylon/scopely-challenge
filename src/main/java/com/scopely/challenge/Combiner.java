package com.scopely.challenge;

import com.scopely.challenge.builder.ValueBuilder;

import java.util.ArrayList;
import java.util.List;

public class Combiner<ValueType, ReturnType> {

    private ValueBuilder<ValueType, ReturnType> valueBuilder;
    private List<ReturnType> comboList = new ArrayList<ReturnType>();
    private List<ValueType> holder = new ArrayList<ValueType>();
    private final ValueType[] valueArray;

    /**
     *
     * @param valueArray
     */
    public Combiner(
            final ValueBuilder<ValueType, ReturnType> valueBuilder,
            final ValueType[] valueArray) {
        if (valueBuilder == null) {
            throw new IllegalArgumentException("valueBuilder cannot be null");
        }

        if (valueArray == null) {
            throw new IllegalArgumentException("valueArray cannot be null");
        }

        this.valueBuilder = valueBuilder;
        this.valueArray = valueArray;
        combineValues(0);
    }

    /**
     *
     * @return
     */
    public List<ReturnType> getComboList() {
        return comboList;
    }

    /**
     *
     * @param startIndex
     */
    private void combineValues(final int startIndex) {
        for (int i = startIndex; i < valueArray.length; i++) {
            holder.add(valueArray[i]);

            valueBuilder.clear();
            for (ValueType newValue : holder) {
                valueBuilder.append(newValue);
            }
            comboList.add(valueBuilder.build());

            if (i < valueArray.length) {
                combineValues(i + 1);
            }

            holder.remove(holder.size() - 1);
        }
    }

}
