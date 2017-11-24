package crossover;

import com.builder.InventoryStateBuilder;
import com.builder.ProductBuilder;
import com.entity.InventoryState;
import com.entity.Product;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.IOUtils.*;

public class MustacheTest {

    private InventoryStateBuilder inventoryStateBuilder = new InventoryStateBuilder();

    @Test
    public void test1() throws IOException, DocumentException {
        Product product = new ProductBuilder()
                .withName("productName")
                .withId(11)
                .build();
        InventoryState inventoryState = inventoryStateBuilder.withProduct(product).build();
        List<InventoryState> inventoryStates = ImmutableList.of(inventoryState);

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("mustache.template");
        StringWriter str = new StringWriter();
        Map<String, Object> context = ImmutableMap.of("items", inventoryStates);
        mustache.execute(str, context).flush();
        System.out.println(str);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("test.pdf")));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, toInputStream(str.toString(), "UTF-8"));
        document.close();

    }
}
