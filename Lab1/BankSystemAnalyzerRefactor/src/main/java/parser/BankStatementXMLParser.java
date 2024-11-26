package parser;

import model.BankTransaction;
import util.DateUtils;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankStatementXMLParser implements BankStatementParser {

    @Override
    public BankTransaction parseFrom(String xmlString) {
        try {
            // Create DocumentBuilderFactory and DocumentBuilder to parse XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            // Assuming xmlString represents a single <transaction> element
            Element element = document.getDocumentElement();
            return parseTransaction(element);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse XML transaction", e);
        }
    }

    @Override
    public List<BankTransaction> parseLinesFrom(List<String> lines) {
        List<BankTransaction> transactions = new ArrayList<>();
        try {
            StringBuilder xmlStringBuilder = new StringBuilder();
            for (String line : lines) {
                xmlStringBuilder.append(line);
            }
            String xmlString = xmlStringBuilder.toString();

            // Create DocumentBuilderFactory and DocumentBuilder to parse XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            // Get all transaction nodes
            NodeList nodeList = document.getElementsByTagName("transaction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    BankTransaction transaction = parseTransaction(element);
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse XML transactions", e);
        }

        return transactions;
    }


    private BankTransaction parseTransaction(Element element) {
        String dateStr = element.getElementsByTagName("date").item(0).getTextContent();
        double amount = Double.parseDouble(element.getElementsByTagName("amount").item(0).getTextContent());
        String description = element.getElementsByTagName("description").item(0).getTextContent();
        return new BankTransaction(DateUtils.parseDate(dateStr), amount, description);
    }
}
