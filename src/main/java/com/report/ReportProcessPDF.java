package com.report;

import com.entity.InventoryState;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Repository("reportProcessPDF")
public class ReportProcessPDF implements ReportProcess {

    @Override
    public void writeData(List<InventoryState> inventoryStates, File file) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfPTable table = createTable();
        addDataInTable(table, inventoryStates);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(table);
        document.close();
    }

    private PdfPTable createTable() {
        float[] columnWidths = {1f, 2f, 2f, 3f, 2f};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("#");
        table.addCell("PRODUCT_NAME");
        table.addCell("QUANTITY");
        table.addCell("DATE");
        table.addCell("TOTAL_PRICE");
        table.setHeaderRows(1);
        return table;
    }

    private void addDataInTable(PdfPTable table, List<InventoryState> inventoryStates) {
        PdfPCell[] pdfPCell = table.getRow(0).getCells();
        for (PdfPCell aPdfPCell : pdfPCell) {
            aPdfPCell.setBackgroundColor(BaseColor.GRAY);
        }
        Integer rowNumber = 0;
        for (InventoryState inventoryState : inventoryStates){
            rowNumber++;
            table.addCell(rowNumber.toString());
            table.addCell(inventoryState.getInventoryStatePK().getProduct().getProductName());
            table.addCell(inventoryState.getQuantity().toString());
            table.addCell(inventoryState.getInventoryStatePK().getStateDate().toString());
            table.addCell(String.valueOf(inventoryState.getInventoryStatePK().getProduct().getPrice() *
            inventoryState.getQuantity()));
        }
    }
}
