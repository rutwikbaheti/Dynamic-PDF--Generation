package com.pdf.generation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.pdf.generation.DTO.Invoice;
import com.pdf.generation.service.PdfService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class PdfController {
	@Autowired
    private PdfService pdfService;
	
	@Operation(summary="Generate PDF")
	@PostMapping("/generate")
    public ResponseEntity<Resource> generatePdf(@Valid @RequestBody Invoice invoice) throws MethodArgumentNotValidException, IOException, DocumentException{
        byte[] pdfBytes = pdfService.generatePdf(invoice);

        // Save the PDF locally
        String filename = "invoice.pdf";
       
        Files.write(Paths.get(System.getProperty("user.home") + "\\Downloads\\" + filename), pdfBytes);

        // Return the PDF as a downloadable attachment
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}