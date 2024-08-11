package com.ecommerce.productservice.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;


public class CamelCaseToUppercaseTablesNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    private Identifier adjustName(final Identifier name) {
        if (name == null) {
            return null;
        }
        final String adjustedName = capitalizeFirstLetterOfEachWord(name.getText());
        return new Identifier(adjustedName, true);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        return adjustName(super.toPhysicalTableName(name, context));
    }


    private String capitalizeFirstLetterOfEachWord(String text) {
        String[] words = text.split("_");
        StringBuilder capitalizedText = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedText.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
            capitalizedText.append("_");
        }

        // Remove the trailing underscore
        if (!capitalizedText.isEmpty()) {
            capitalizedText.setLength(capitalizedText.length() - 1);
        }

        return capitalizedText.toString();
    }
}
