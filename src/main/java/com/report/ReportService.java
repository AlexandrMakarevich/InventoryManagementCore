package com.report;

import com.constant.ReportFormat;
import com.dao.InventoryStateDao;
import com.entity.InventoryState;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service("reportService")
public class ReportService {

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    @Resource(name = "reportInitializer")
    private ReportInitializer reportInitializer;

    public void generateReport(LocalDateTime localDateTime, ReportFormat reportFormat, String url) throws IOException, DocumentException {
        List<InventoryState> inventoryStates = inventoryStateDao.getActualInventoryStateByDate(localDateTime);
        ReportProcess reportProcess = reportInitializer.initializeReport(reportFormat);
        File file = new File(url);
        reportProcess.writeData(inventoryStates, file);
    }
}
