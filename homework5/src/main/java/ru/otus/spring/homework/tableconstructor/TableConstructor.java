package ru.otus.spring.homework.tableconstructor;

import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;

@Component
public class TableConstructor {

    private static final int MAX_WIDTH = 200;

    String buildTable(Object[][] objects) {
        TableModel model = new ArrayTableModel(objects);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.oldschool);

        return tableBuilder.build().render(MAX_WIDTH);
    }
}
