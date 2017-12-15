package com.report;

import com.entity.InventoryState;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Repository("reportProcessPDF")
public class ReportProcessPDF implements ReportProcess {

    private ReportProcessHTML reportProcessHTML = new ReportProcessHTML();

    @Override
    public void writeData(List<InventoryState> inventoryStates, File file) throws IOException, DocumentException {
        File htmlFile = reportProcessHTML.generateHTMLReport(inventoryStates);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(htmlFile));
        document.close();
    }
}
