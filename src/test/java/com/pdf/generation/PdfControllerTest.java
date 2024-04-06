package com.pdf.generation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.itextpdf.text.DocumentException;
import com.pdf.generation.DTO.Invoice;
import com.pdf.generation.DTO.Item;
import com.pdf.generation.controller.PdfController;
import com.pdf.generation.service.PdfService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PdfControllerTest {
	@InjectMocks
	PdfController controller;
	
	@Mock
	PdfService service;
	
	@Test
    void testGeneratePdf() throws IOException, MethodArgumentNotValidException, DocumentException {

        Invoice invoice = new Invoice();
        invoice.setSeller("INV-123");
        invoice.setSellerAddress("John Doe");
        invoice.setSellerGstin("1");
        invoice.setBuyer("INV-123");
        invoice.setBuyerAddress("John Doe");
        invoice.setBuyerGstin("1");
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("a");
        item.setQuantity("1");
        item.setAmount(1);
        item.setRate(1);
        items.add(item);
        invoice.setItems(items);
        
        byte[] pdfBytes = invoice.toString().getBytes();
        when(service.generatePdf(Mockito.any(Invoice.class))).thenReturn(pdfBytes);

        ResponseEntity<Resource> responseEntity = controller.generatePdf(invoice);

        HttpHeaders headers = responseEntity.getHeaders();
        
        assertEquals(MediaType.APPLICATION_PDF, headers.getContentType());

        Resource resource = responseEntity.getBody();
        byte[] downloadedPdfBytes = new byte[resource.getInputStream().available()];
        resource.getInputStream().read(downloadedPdfBytes);
        assertEquals(pdfBytes.length, downloadedPdfBytes.length);
        assertEquals(new String(pdfBytes), new String(downloadedPdfBytes));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resource.getInputStream().transferTo(outputStream);
        byte[] savedPdfBytes = outputStream.toByteArray();
        assertEquals(pdfBytes.length, savedPdfBytes.length);
        assertEquals(new String(pdfBytes), new String(savedPdfBytes));
    }
}
