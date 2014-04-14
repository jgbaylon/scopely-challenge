package com.scopely.challenge.builder;

public class StringValueBuilderImpl implements ValueBuilder<String, String> {

    private StringBuilder builder = new StringBuilder();

    @Override
    public ValueBuilder<String, String> append(String value) {
        builder.append(value).append("-");
        return this;
    }

    @Override
    public String build() {
        builder.deleteCharAt(builder.lastIndexOf("-"));
        return builder.toString();
    }

}
