package com.scopely.challenge.builder;

public interface ValueBuilder<ValueType, ReturnType> {

    /**
     *
     * @param value
     * @return
     */
    public ValueBuilder<ValueType, ReturnType> append(final ValueType value);

    /**
     *
     * @return
     */
    public ReturnType build();

    /**
     *
     * @return
     */
    public ValueBuilder<ValueType, ReturnType> clear();

}
