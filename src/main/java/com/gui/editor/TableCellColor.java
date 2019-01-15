package com.gui.editor;

import com.gui.domain.InstrumentRecord;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TableCellColor implements Callback<TableColumn<InstrumentRecord, String>, TableCell<InstrumentRecord, String>> {
    @Override
    public TableCell<InstrumentRecord, String> call(TableColumn<InstrumentRecord, String> param) {
        return new TableCell<InstrumentRecord, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !item.equals("")) {
                    if (item.contains("-"))
                        this.setTextFill(Color.RED);
                    else
                        this.setTextFill(Color.GREEN);
                    setText(item);
                }
            }
        };
    }
}


