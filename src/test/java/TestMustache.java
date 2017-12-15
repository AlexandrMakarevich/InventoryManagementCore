import com.BaseTest;
import com.builder.InventoryStateBuilder;
import com.builder.ProductBuilder;
import com.entity.InventoryState;
import com.entity.Product;
import com.google.common.collect.ImmutableList;
import com.itextpdf.text.DocumentException;
import com.report.ReportProcess;
import com.report.ReportProcessHTML;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestMustache extends BaseTest {

    private InventoryStateBuilder inventoryStateBuilder = new InventoryStateBuilder();

    private ReportProcessHTML reportProcessHTML = new ReportProcessHTML();

    @Resource(name = "reportProcessPDF")
    private ReportProcess reportProcess;

    @Test
    public void test1() throws IOException, DocumentException {
        Product product = new ProductBuilder()
                .withName("productName")
                .withId(11)
                .build();
        
        InventoryState inventoryState = inventoryStateBuilder.withProduct(product).build();
        InventoryState inventoryState1 = inventoryStateBuilder.withProduct(product).build();
        List<InventoryState> inventoryStates = ImmutableList.of(inventoryState, inventoryState1);
//        reportProcessHTML.generateHTMLReport(inventoryStates);

        reportProcess.writeData(inventoryStates, new File("target/report.pdf"));
    }
}
