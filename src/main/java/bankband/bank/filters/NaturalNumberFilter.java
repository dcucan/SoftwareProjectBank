package bankband.bank.filters;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class NaturalNumberFilter implements UnaryOperator<TextFormatter.Change> {

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if (change.getText().matches("[0-9]*")) {
            return change;
        }

        return null;
    }

    public static TextFormatter<String> getFormatter() {
        return new TextFormatter<>(new NaturalNumberFilter());
    }
}
