package com.filmverleih.filmverleih.pdfGentators;

import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Class for generating a warning pdf for a rental
 * author: Torvalds
 */
public class WarningPdfGenerator {

    /**
     * Generates a warning pdf
     * @param moviename name of the movie
     * @param StartDate date of the rental
     * @param EndDate date of the return
     * @return true if the pdf was generated
     */
    public static Boolean generatePdf(String moviename, String StartDate, String EndDate) {
            try {
                PDDocument document = new PDDocument();

                // Füge eine Seite zum Dokument hinzu
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                // Starte den Schreibvorgang für die Seite
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Setze die Schriftart und -größe für den Titel
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.setLeading(30);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Mahnung");
                contentStream.endText();

                // Setze die Schriftart und -größe für den Inhalt
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(16);

                // Schreibe den Inhalt der Mahnung
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Sehr geehrter Kunde,");
                contentStream.newLine();
                contentStream.showText("wir möchten Sie höflich daran erinnern, dass die Verleihdauer für folgende Videos");
                contentStream.newLine();
                contentStream.showText("überschritten wurde:");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Video: " + moviename);
                contentStream.newLine();
                contentStream.showText("Verleihdatum: " + StartDate);
                contentStream.newLine();
                contentStream.showText("Überfällig seit: " + EndDate);
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Bitte bringen Sie das Video so bald wie möglich zurück.");
                contentStream.newLine();
                contentStream.showText("Für weitere Informationen stehen wir Ihnen gerne zur Verfügung.");
                contentStream.endText();

                // Setze die Linienfarbe und zeichne eine Linie
                contentStream.setStrokingColor(Color.decode("#FFFF3D"));
                contentStream.moveTo(50, 660);
                contentStream.lineTo(550, 660);
                contentStream.stroke();

                File file = new File("src/main/resources/com/filmverleih/filmverleih/icons/logo.png");
                PDImageXObject logo = PDImageXObject.createFromFileByContent(file, document);
                float logoWidth = logo.getWidth() * 0.3f; // Skalierungsfaktor von 0.3
                float logoHeight = logo.getHeight() * 0.3f; // Skalierungsfaktor von 0.3
                contentStream.drawImage(logo, 550 - logoWidth, 800 - logoHeight, logoWidth, logoHeight);

            // Schließe den Schreibvorgang
            contentStream.close();

            // Speichere das Dokument
            File outputFile = new File("verleihdatenueberschreitung_mahnung.pdf");
            document.save(outputFile);

            // Schließe das Dokument
            document.close();

            // Öffne das Dokument automatisch
            Desktop.getDesktop().open(outputFile);

                LoggerUtility.logger.info("Remind pdf created successfully...");
                return true;
        } catch (IOException e) {
                LoggerUtility.logger.info("Remind pdf could not be created...");
            return false;
        }
    }
}
