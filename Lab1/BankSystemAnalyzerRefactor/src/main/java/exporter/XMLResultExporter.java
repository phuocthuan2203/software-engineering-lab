package exporter;

import model.BankTransaction;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.List;

public class XMLResultExporter implements ResultExporter {

    @Override
    public void export(List<BankTransaction> transactions, String outputPath) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("transactions");
            document.appendChild(root);

            for (BankTransaction transaction : transactions) {
                Element transactionElement = document.createElement("transaction");

                Element date = document.createElement("date");
                date.appendChild(document.createTextNode(transaction.getDate().toString()));
                transactionElement.appendChild(date);

                Element amount = document.createElement("amount");
                amount.appendChild(document.createTextNode(Double.toString(transaction.getAmount())));
                transactionElement.appendChild(amount);

                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(transaction.getDescription()));
                transactionElement.appendChild(description);

                root.appendChild(transactionElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(outputPath));

            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
