package com.report;

import com.entity.InventoryState;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ReportProcessHTML {


    public File generateHTMLReport(List<InventoryState> inventoryStates) throws IOException {
        loadProperty();
        File htmlFile = new File(System.getProperty("urlHtmlFile"));
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("mustache.template");
        BigDecimal totalPrice = countTotalPrice(inventoryStates);
        Map<String, Object> context = ImmutableMap.of("items", inventoryStates,
                "price", totalPrice);
        mustache.execute(new PrintWriter(htmlFile), context).flush();
        return htmlFile;
    }

    public BigDecimal countTotalPrice(List<InventoryState> inventoryStates) {
        BigDecimal countPrice = new BigDecimal(0);
        for (InventoryState inventoryState : inventoryStates) {
            countPrice = countPrice.add(inventoryState.calculateItemCost());
        }
        return countPrice;
    }

    public void loadProperty() throws IOException{
        Properties properties = System.getProperties();
        InputStream inputStream = getClass().getResourceAsStream("/config-file.properties");
        properties.load(inputStream);
        System.setProperties(properties);
        inputStream.close();
    }
}
