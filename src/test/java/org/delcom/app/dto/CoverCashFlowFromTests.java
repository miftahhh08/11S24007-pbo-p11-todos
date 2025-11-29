package org.delcom.app.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CoverCashFlowFromTests {

    // Konstanta ukuran file maksimal di DTO adalah 5MB
    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L; 

    @Mock
    private MultipartFile mockFile;

    private UUID testCashFlowId;

    @BeforeEach
    void setUp() {
        // Inisialisasi mock objek sebelum setiap pengujian
        MockitoAnnotations.openMocks(this);
        testCashFlowId = UUID.randomUUID();
    }

    @Test
    void testCoverCashFlowFromGettersAndSetters() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setCashFlowId(testCashFlowId);
        form.setAttachmentFile(mockFile);

        assertEquals(testCashFlowId, form.getCashFlowId());
        assertEquals(mockFile, form.getAttachmentFile());
    }

    // --- Pengujian Helper Methods ---

    @Test
    void testIsEmpty_withEmptyFile() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Konfigurasi mockFile agar dianggap empty
        when(mockFile.isEmpty()).thenReturn(true);

        assertTrue(form.isEmpty());
    }

    @Test
    void testIsEmpty_withNullFile() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        // attachmentFile adalah null secara default
        assertTrue(form.isEmpty());
    }

    @Test
    void testIsValidImage_Success_ValidPng() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Konfigurasi mock untuk tipe file yang valid (misal: PNG)
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getContentType()).thenReturn("image/png");

        assertTrue(form.isValidImage());
    }

    @Test
    void testIsValidImage_Failure_InvalidTypePdf() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Konfigurasi mock untuk tipe file yang tidak valid (misal: PDF)
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getContentType()).thenReturn("application/pdf");

        assertFalse(form.isValidImage());
    }

    @Test
    void testIsValidImage_Failure_EmptyFile() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        // File dianggap kosong (sesuai logika isEmpty)
        when(mockFile.isEmpty()).thenReturn(true);
        
        assertFalse(form.isValidImage());
    }

    @Test
    void testIsSizeValid_Success_UnderLimit() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Menguji file di bawah batas 5MB (misal: 1MB)
        when(mockFile.getSize()).thenReturn(1024L * 1024L);

        assertTrue(form.isSizeValid());
    }

    @Test
    void testIsSizeValid_Failure_OverLimit() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Menguji file sedikit di atas batas 5MB
        when(mockFile.getSize()).thenReturn(MAX_FILE_SIZE + 1L);

        assertFalse(form.isSizeValid());
    }
}