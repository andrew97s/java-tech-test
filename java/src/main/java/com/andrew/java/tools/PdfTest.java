package com.andrew.java.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * @author tongwenjin
 * @since 2023/10/25
 */
public class PdfTest {

    public static void main(String[] args) {
        image2PdfBeta("C:\\Users\\Administrator\\Desktop\\demoz1.txt");
    }

    public static void image2PdfBeta(String filePath) {
        String base64Content = readTextFile(filePath);

        String output = "C:\\Users\\Administrator\\Desktop\\output-beta.pdf";
        try {
            byte[] imageBytes = Base64.getMimeDecoder().decode(base64Content);

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdfImage = createPDImageFromBytes(imageBytes, document);
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float imageWidth = pdfImage.getWidth();
            float imageHeight = pdfImage.getHeight();

            // 计算图像缩放比例，使其适应页面
            float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);
            float scaledWidth = imageWidth * scale;
            float scaledHeight = imageHeight * scale;
            float x = (pageWidth - scaledWidth) / 2;
            float y = (pageHeight - scaledHeight) / 2;

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(pdfImage, x, y, scaledWidth, scaledHeight);
            contentStream.close();

            document.save(output);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PDImageXObject createPDImageFromBytes(byte[] imageBytes, PDDocument document) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        return LosslessFactory.createFromImage(document, image);
    }

    public static String readTextFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)) ) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
