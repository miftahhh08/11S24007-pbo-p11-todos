package org.delcom.app.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CoverCashFlowFormTests {

    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L; 

    @Mock
    private MultipartFile mockFile;

    private UUID testCashFlowId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCashFlowId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test: Getter dan Setter untuk ID dan File")
    void testCoverCashFlowFromGettersAndSetters() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setCashFlowId(testCashFlowId);
        form.setAttachmentFile(mockFile);

        assertEquals(testCashFlowId, form.getCashFlowId());
        assertEquals(mockFile, form.getAttachmentFile());
    }

    // --- Pengujian Helper Methods ---

    @Test
    @DisplayName("Test: isEmpty() -> True jika file null atau kosong")
    void testIsEmpty() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        
        // Kasus 1: File null
        assertTrue(form.isEmpty());

        // Kasus 2: File kosong
        form.setAttachmentFile(mockFile);
        when(mockFile.isEmpty()).thenReturn(true);
        assertTrue(form.isEmpty());
    }

    @Test
    @DisplayName("Test: getOriginalFilename() -> Mengambil nama file")
    void testGetOriginalFilename() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        
        // Kasus 1: File null
        assertNull(form.getOriginalFilename());
        
        // Kasus 2: File ada
        form.setAttachmentFile(mockFile);
        when(mockFile.getOriginalFilename()).thenReturn("bukti.jpg");
        assertEquals("bukti.jpg", form.getOriginalFilename());
    }

    // --- Pengujian Validasi Gambar ---

    @Test
    @DisplayName("Test: isValidImage() -> Sukses untuk tipe image yang valid")
    void testIsValidImage_Success() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        String[] validTypes = {"image/jpeg", "image/png", "image/gif", "image/webp"};
        when(mockFile.isEmpty()).thenReturn(false);

        for (String type : validTypes) {
            when(mockFile.getContentType()).thenReturn(type);
            assertTrue(form.isValidImage(), "Should be valid for type: " + type);
        }
    }

    @Test
    @DisplayName("Test: isValidImage() -> Gagal untuk file kosong atau tipe non-image")
    void testIsValidImage_Failure() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        
        // Kasus 1: File kosong
        form.setAttachmentFile(mockFile);
        when(mockFile.isEmpty()).thenReturn(true);
        assertFalse(form.isValidImage());

        // Kasus 2: Tipe non-image
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getContentType()).thenReturn("application/pdf");
        assertFalse(form.isValidImage());
        
        // Kasus 3: Content type null
        when(mockFile.getContentType()).thenReturn(null);
        assertFalse(form.isValidImage());
    }

    // --- Pengujian Validasi Ukuran ---

 // Konstanta ukuran file maksimal di DTO adalah 5MB
// ...

    @Test
    @DisplayName("Test: isSizeValid() -> Sukses untuk ukuran di bawah atau sama dengan MAX_FILE_SIZE")
    void testIsSizeValid_Success() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Kasus 1: Ukuran tepat di batas maksimum (Memverifikasi <= MAX_FILE_SIZE)
        when(mockFile.getSize()).thenReturn(MAX_FILE_SIZE);
        assertTrue(form.isSizeValid());

        // Kasus 2: Ukuran di bawah batas maksimum (Memverifikasi <= MAX_FILE_SIZE)
        when(mockFile.getSize()).thenReturn(1024L * 1024L); 
        assertTrue(form.isSizeValid());
    }

    @Test
    @DisplayName("Test: isSizeValid() -> Gagal untuk ukuran di atas MAX_FILE_SIZE")
    void testIsSizeValid_Failure_OverLimit() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        form.setAttachmentFile(mockFile);

        // Ukuran sedikit di atas batas maksimum (Memverifikasi > MAX_FILE_SIZE)
        when(mockFile.getSize()).thenReturn(MAX_FILE_SIZE + 1L);
        assertFalse(form.isSizeValid());
    }

    @Test
    @DisplayName("Test: isSizeValid() -> Gagal jika attachmentFile null")
    void testIsSizeValid_Failure_NullFile() {
        CoverCashFlowFrom form = new CoverCashFlowFrom();
        // attachmentFile adalah null
        assertFalse(form.isSizeValid()); // Memverifikasi != null
    }
}