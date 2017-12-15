package com;

import com.constant.ReportFormat;
import com.itextpdf.text.DocumentException;
import com.report.ReportService;
import org.junit.Test;
import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

public class TestReportService extends BaseTest {

    @Resource(name = "reportService")
    private ReportService reportService;

    @Test
    public void test() throws IOException, DocumentException {
        String fileName = "target/report.pdf";
        reportService.generateReport(LocalDateTime.now(), ReportFormat.PDF, fileName);
    }
}
