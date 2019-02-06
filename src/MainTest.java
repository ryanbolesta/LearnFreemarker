package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class MainTest {

    public static void main(String[] args) throws Exception {

        // 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.

        Configuration cfg = new Configuration();

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(MainTest.class, "templates");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);


        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        input.put("title", "FreeMarker Example");

        input.put("exampleObject", new ValueExampleObject("Example Name", 111));

        List<ValueExampleObject> systems = new ArrayList<ValueExampleObject>();
        systems.add(new ValueExampleObject("John Doe" , 543));
        systems.add(new ValueExampleObject("Jane Smith" , 897));
        systems.add(new ValueExampleObject("Dummy Guy" , 123));
        systems.add(new ValueExampleObject("Test Person" , 32));
        input.put("systems", systems);

        // 2.2. Get the template

        Template template = cfg.getTemplate("helloworld.ftl");

        // 2.3. Generate the output

        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        Writer fileWriter = new FileWriter(new File("output.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
        
        // Generate PDF from newly create html file
        generatePDFFromHTML("output.html");

    }
    
    private static void generatePDFFromHTML(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
          new FileOutputStream("output.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
          new FileInputStream(filename));
        document.close();
    }

    
  
 
}