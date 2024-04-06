package com.pdf.generation.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdf.generation.DTO.Invoice;
import com.pdf.generation.DTO.Item;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public byte[] generatePdf(Invoice invoice) throws DocumentException, IOException {
    	// Create a new Document
        Document document = new Document();

        // Create a ByteArrayOutputStream to hold the PDF content
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create a PdfWriter associated with the Document
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
     
        Font font = new Font(Font.FontFamily.COURIER,14,Font.BOLD, BaseColor.BLACK);
        
        // Open the Document for writing
        document.open();

        // Create a table with 2 rows and 4 columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        // Set width of the table to 100% of the page width
        table.setWidths(new float[]{25, 25, 25, 25});

        // Set height of the table to fit the content
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());

        // Add first row with two columns
        Phrase sellerPhrase = new Phrase();
        sellerPhrase.add(new Chunk("Seller:\n", font));
        sellerPhrase.add(new Chunk(invoice.getSeller()+"\n", font));
        sellerPhrase.add(new Chunk(invoice.getSellerAddress()+"\n", font));
        sellerPhrase.add(new Chunk("GSTIN: "+invoice.getSellerGstin()+"\n", font));
        PdfPCell sellerCell = new PdfPCell(sellerPhrase);
        Phrase buyerPhrase = new Phrase();
        buyerPhrase.add(new Chunk("Buyer:\n", font));
        buyerPhrase.add(new Chunk(invoice.getBuyer()+"\n", font));
        buyerPhrase.add(new Chunk(invoice.getBuyerAddress()+"\n", font));
        buyerPhrase.add(new Chunk("GSTIN :"+invoice.getBuyerGstin()+"\n", font));
        PdfPCell buyerCell = new PdfPCell(buyerPhrase);

        // Merge cells for first row
        sellerCell.setColspan(2);
        buyerCell.setColspan(2);

        // Set padding and alignment for seller and buyer cells
        sellerCell.setPadding(30);
        
        buyerCell.setPadding(30);
        

        table.addCell(sellerCell);
        table.addCell(buyerCell);

        // Add second row with four columns
        PdfPCell itemCell = new PdfPCell(new Phrase("Item",font));
        PdfPCell quantityCell = new PdfPCell(new Phrase("Quantity",font));
        PdfPCell rateCell = new PdfPCell(new Phrase("Rate",font));
        PdfPCell amountCell = new PdfPCell(new Phrase("Amount",font));
        
        // Set padding and alignment for item cells
        itemCell.setPadding(8);
        itemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantityCell.setPadding(8);
        quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rateCell.setPadding(8);
        rateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        amountCell.setPadding(8);
        amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(itemCell);
        table.addCell(quantityCell);
        table.addCell(rateCell);
        table.addCell(amountCell);
        
        for(Item i : invoice.getItems()) {
        	PdfPCell i_itemCell = new PdfPCell(new Phrase(i.getName(),font));
            PdfPCell i_quantityCell = new PdfPCell(new Phrase(i.getQuantity(),font));
            PdfPCell i_rateCell = new PdfPCell(new Phrase(Double.toString(i.getRate()),font));
            PdfPCell i_amountCell = new PdfPCell(new Phrase(Double.toString(i.getAmount()),font));
            
            i_itemCell.setPadding(8);
            i_itemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            i_quantityCell.setPadding(8);
            i_quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            i_rateCell.setPadding(8);
            i_rateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            i_amountCell.setPadding(8);
            i_amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell(i_itemCell);
            table.addCell(i_quantityCell);
            table.addCell(i_rateCell);
            table.addCell(i_amountCell);
        }

        // Add the table to the document
        document.add(table);


        // Close the Document
        document.close();

        // Return the PDF content as a byte array
        return outputStream.toByteArray();
    }
}
