package com.example.itext.demo;

import com.example.itext.demo.velocity.MyFontProvider;
import com.example.itext.demo.velocity.VelocityEngineInstance;
import com.example.itext.demo.velocity.VelocityUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.MethodInvocationException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class html2PDF {
    public static final String TEMP_PATH = "C:\\hong\\output\\iText";
    public static final String  TEMPLATE_ID = "Certificate_logo";
    public static final String  SUFFIX = "pdf";

    public static void main(String[] args) throws IOException {
        html2pdf();
    }

    public static void html2pdf() throws IOException {
        Map<String, Object> map = getData();
        File outPutFile = getFile(map.get("orderNo").toString(), TEMPLATE_ID, SUFFIX);
        Document document = null;
        try {
            OutputStream os = new FileOutputStream(outPutFile);
            document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            String content = getTemplate(TEMPLATE_ID);
            String piContent = VelocityUtil.evaluateString(content, map);

            // set font
            MyFontProvider myFontProvider = new MyFontProvider();
            myFontProvider.setUseUnicode(true);
            CssAppliers cssAppliers = new CssAppliersImpl(myFontProvider);
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

            InputStream is = new ByteArrayInputStream(piContent.getBytes(UTF_8));

            XMLWorkerHelper.getInstance()
                    .parseXHtml(writer, document, is, null, UTF_8, myFontProvider);

            document.close();
            os.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            System.out.println("get doc error =====================================>  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Velocity error =====================================>  " + e.getMessage());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    public static Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("serviceType", "PSI");
        map.put("serviceName", "Mr Price Group Ltd");
        map.put("companyName", "65 N.M.R. Ave, Durban 4001, South Africa");
        map.put("beneficiary", "Compagnie Malagasy de textille(F0001335)");
        map.put("orderNo", "R-Cloud-22223952");
        map.put("productDescription","ELASTICATED SHORT WITH HRB TAPE AS TIES");
        map.put("refNo","225385");
        map.put("quantityPrice","2000");
        map.put("inspectionDate","31-Oct-2022");
        map.put("result","PASSED");
        map.put("comment", "We, QIMA Limited, hereby certify that Compagnie Malagasy de textille(F0001335) can proceed" +
                "further with this Certificate and use it for delivery regarding above Products and that MRP" +
                "Group Apparel has decided that above goods are in good order and condition with contractual\n" +
                "specifications and as per Inspection Standards :\n" +
                "• ANSI/ASQ Standard Z.1.4-2008\n" +
                "• AQL Level I\n" +
                "• Critical Not Allowed, Major 2.5, Minor 4.0 are accepted");
        return map;
    }

    public static File getFile(String orderNo, String templateId,String suffix) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        String path = TEMP_PATH + "\\" + sdf.format(new Date());
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(path + "\\" + templateId + "_" + orderNo
                + "." + suffix);
    }

    public static String getTemplate(String templateId) throws IOException {
        String content = null;
        try {
            File file = ResourceUtils.getFile("classpath:file/template/" + templateId + ".html");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[10];
            while (reader.read(buffer) != -1) {
                stringBuilder.append(new String(buffer));
                buffer = new char[10];
            }
            reader.close();

            content = stringBuilder.toString();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("get template failed.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("get file content failed.");
        }
        return content;
    }

}
