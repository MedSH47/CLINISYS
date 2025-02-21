/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csys.template.util;

import com.csys.template.config.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderFooterPDF extends PdfPageEventHelper {

    Image img;
    String raisonSoc;
    String user;
    PdfTemplate total;
    Image image;

    public HeaderFooterPDF(Image img, String raisonSoc, String user) {
        this.img = img;
        this.raisonSoc = raisonSoc;
        this.user = user;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();

//        try {
//            canvas.addImage(img);
//        } catch (DocumentException ex) {
//            System.out.println(ex.getMessage());
//        }
        Font font = FontFactory.getFont("Arial", 8);
        Phrase CLINISYSErp = new Phrase("CLINISYS Erp", font);
        Phrase raiSoc = new Phrase(raisonSoc, font);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Phrase EditeDate = new Phrase("Edité le: " + dateFormat.format(date), font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, raiSoc, document.left(), document.top() + 15, 0);//820
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, CLINISYSErp, document.right(), document.top() + 25, 0);//830
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, EditeDate, document.right(), document.top() + 15, 0);//820
        canvas.moveTo(document.left(), document.top() + 10);
        canvas.lineTo(document.right(), document.top() + 10);
        canvas.stroke();

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        Font font = FontFactory.getFont("Arial", 8);

        PdfPTable table = new PdfPTable(2);
        try {
            table.setWidths(new int[]{30, 2});
            table.setTotalWidth(document.right() - 18);

            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase(String.format("Page %d /", writer.getPageNumber()), font));
            PdfPCell cell = new PdfPCell(image);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            PdfContentByte canvas1 = writer.getDirectContent();
            table.writeSelectedRows(0, -1, 36, 27, canvas1);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
        Phrase userPhrase = new Phrase("Imprimé par " + user, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, userPhrase, document.left() + 70, document.bottom() - 20, 0);
        canvas.moveTo(document.left(), document.bottom() - 10);
        canvas.lineTo(document.right(), document.bottom() - 10);
        canvas.stroke();
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(18, 12);
        try {
            image = Image.getInstance(total);
            image.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        Font font = FontFactory.getFont("Arial", 8);
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber()), font), 2, 2, 0);
    }
}
