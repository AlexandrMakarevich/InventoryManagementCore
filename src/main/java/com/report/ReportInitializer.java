package com.report;

import com.constant.ReportFormat;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.Map;

@Repository("reportInitializer")
public class ReportInitializer {

    @Resource(name = "initializerReport")
    private Map<ReportFormat, ReportProcess> reportProcessMap;

    public ReportProcess initializeReport(ReportFormat reportFormat) {
        ReportProcess reportProcess = reportProcessMap.get(reportFormat);
        if (reportProcess == null) {
            throw new IllegalArgumentException("Inserted wrong format of report" + reportFormat);
        }
        return  reportProcess;
    }
}
