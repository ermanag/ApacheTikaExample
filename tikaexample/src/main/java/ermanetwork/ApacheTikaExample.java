package ermanetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;


public class ApacheTikaExample {

    public static void main(String[] args) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        FileItem fileItem = factory.createItem("formFieldName", "application/pdf", false,
                "C:\\Users\\erman.ag\\Desktop\\test.pdf");
        findFileExtensionwithFile("");
        findFileExtensionwithFileItem(fileItem);
        findMetadata();

    }

    public static String findFileExtensionwithFileItem(FileItem file) {
        try {
            TikaConfig config= new TikaConfig();
            Detector detector = config.getDetector();
            Metadata metadata = new Metadata();
            metadata.add(Metadata.RESOURCE_NAME_KEY, file.getName());
            InputStream stream = TikaInputStream.get(file.getInputStream());
            MediaType mediaType = detector.detect(stream, metadata);
            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
            String suffix = mimeType.getExtension();
            if(!StringUtil.isNullOrEmpty(suffix))
                return suffix;
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String findFileExtensionwithFile(String file) {
        try {
            TikaConfig config= new TikaConfig();
            Detector detector = config.getDetector();
            Metadata metadata = new Metadata();
            InputStream stream = TikaInputStream.get(new File(file) , metadata);
            MediaType mediaType = detector.detect(stream, metadata);
            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
            String suffix = mimeType.getExtension();
            if(!StringUtil.isNullOrEmpty(suffix))
                return suffix;
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void findMetadata(){
        Metadata metadata = new Metadata();
        ContentHandler handler = new DefaultHandler();
        Parser parser = new PDFParser();
        ParseContext context = new ParseContext();

        parser.parse(new FileInputStream("C:\\Users\\erman.ag\\Desktop\\test.pdf"),
                handler,
                metadata,
                context);
        String[] names = metadata.names();

        for (String name : names)
            System.out.printf("%s : %s %n", name, metadata.get(name));
    }
}
