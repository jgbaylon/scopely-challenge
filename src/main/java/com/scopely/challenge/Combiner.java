package com.scopely.challenge;

import com.scopely.challenge.builder.StringValueBuilderImpl;
import com.scopely.challenge.builder.ValueBuilder;

import java.util.ArrayList;
import java.util.List;

public class Combiner<ValueType, ReturnType> {

    private List<ReturnType> comboList = new ArrayList<ReturnType>();
    private List<ValueType> holder = new ArrayList<ValueType>();
    private final ValueType[] valueArray;

    /**
     *
     * @param valueArray
     */
    public Combiner(final ValueType[] valueArray) {
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

            ValueBuilder<ValueType, ReturnType> builder = (ValueBuilder<ValueType, ReturnType>) new StringValueBuilderImpl();
            for (ValueType newValue : holder) {
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
