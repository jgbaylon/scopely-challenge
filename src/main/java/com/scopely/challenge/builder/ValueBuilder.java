package com.scopely.challenge.builder;

public interface ValueBuilder<ValueType, ReturnType> {

    public ValueBuilder<ValueType, ReturnType> append(final ValueType value);
    public ReturnType build();

}
