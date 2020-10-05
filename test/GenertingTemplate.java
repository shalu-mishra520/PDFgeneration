package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.lowagie.text.DocumentException;

public class GenertingTemplate {
	

	public static void main(String[] args) throws IOException, DocumentException {
		
		
		
		ArrayList<UserClass> user=new ArrayList<>();
		user.add(new UserClass(1,"Shalu",23,"Newgen","shalumisr"));
		user.add(new UserClass(2,"sneha",29,"Newgen","shalumis"));
		user.add(new UserClass(3,"priya",24,"Newgen","shalumis"));
		
		Context context = new Context();
		ClassLoaderTemplateResolver templateresolver=new ClassLoaderTemplateResolver();
		templateresolver.setPrefix("template/");
		templateresolver.setSuffix(".html");
		templateresolver.setTemplateMode("HTML");
		
		System.out.println("print 1");
		TemplateEngine templateengine=new TemplateEngine();
		templateengine.setTemplateResolver(templateresolver);
		System.out.println("print 2");
		Properties properties = new Properties();
		String propertiesFileName;
		
		System.out.println("print 3");
		
		propertiesFileName = "tamil.properties";
		InputStream inputStream = GenertingTemplate.class.getClassLoader().getResourceAsStream(propertiesFileName);
		properties.load(inputStream);
		context.setVariable("name", properties.getProperty("name"));
		context.setVariable("age", properties.getProperty("age"));
		context.setVariable("organisation", properties.getProperty("organisation"));
		context.setVariable("panid", properties.getProperty("panid"));
		context.setVariable("janashakthi", properties.getProperty("janashakthi"));
		
		System.out.println("janashakti:"+properties.getProperty("janashakthi"));
		System.out.println("Git Changes to be done");
		
		String html=templateengine.process("test1", context);
		
		String pathtofont = "C:\\Windows\\Fonts\\NirmalaUIBold.ttf";
		//String fontname = GenertingTemplate.class.getResource(pathtofont).toString();
		//FontFactory.register(fontname);
		FileUtils.writeStringToFile(new File("C:\\Users\\shalu\\Desktop\\a.html"), html,"UTF-8");
		ITextRenderer renderer = new ITextRenderer();
		//renderer.setPDFEncryption(pdfEncryption);
		//renderer.getFontResolver().addFont(fontname,"UTF-8",BaseFont.EMBEDDED);
		renderer.setDocumentFromString(html);
		
		renderer.layout();
		
		
		File f=new File("C:\\Users\\shalu\\Desktop\\shalu.pdf");
		if(f.exists()){
			f.delete();
		}
		FileOutputStream fos = new FileOutputStream(f);
		
		PdfDocument p=new PdfDocument();
		
		renderer.createPDF(fos);
		
		fos.close();
	}

}
