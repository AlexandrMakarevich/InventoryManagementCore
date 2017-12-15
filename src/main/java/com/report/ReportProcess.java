package com.report;

import com.entity.InventoryState;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ReportProcess {

    void writeData(List<InventoryState>inventoryStates, File file) throws IOException, DocumentException;
}
