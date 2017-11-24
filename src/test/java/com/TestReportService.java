package com;

import com.constant.ReportFormat;
import com.itextpdf.text.DocumentException;
import com.report.ReportService;
import org.junit.Test;
import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class TestReportService extends BaseTest {

    @Resource(name = "reportService")
    private ReportService reportService;

    @Test
    public void test() throws FileNotFoundException, DocumentException {
        String fileName = "target/report.csv";
        reportService.generateReport(LocalDateTime.now(), ReportFormat.CSV, fileName);
    }
}
