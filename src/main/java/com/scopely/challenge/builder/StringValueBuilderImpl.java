package com.scopely.challenge.builder;

public class StringValueBuilderImpl implements ValueBuilder<String, String> {

    private static final String DASH = "-";

    private StringBuilder builder = new StringBuilder();

    /**
     *
     * @param value
     * @return
     */
    @Override
    public ValueBuilder<String, String> append(final String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException("value cannot be null or empty");
        }
        builder.append(value).append(DASH);
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public String build() {
        builder.deleteCharAt(builder.lastIndexOf(DASH));
        return builder.toString();
    }

}
