import com.entity.InventoryState;
import com.report.ReportProcessHTML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<InventoryState> inventoryStates = new ArrayList<>();
        ReportProcessHTML reportProcessHTML = new ReportProcessHTML();
        reportProcessHTML.generateHTMLReport(inventoryStates);
    }
}
