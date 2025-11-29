package org.delcom.app.dto;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

public class CoverCashFlowFrom { // Nama DTO asli: CoverCashFlowFrom

    @NotNull(message = "ID CashFlow tidak boleh kosong")
    private UUID cashFlowId; // ID transaksi CashFlow yang akan dilampiri

    @NotNull(message = "File lampiran (bukti) tidak boleh kosong")
    private MultipartFile attachmentFile; // File yang diunggah

    // --- Konstanta Validasi ---
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    // ✅ PERBAIKAN: Constructor tidak memiliki tipe pengembalian (void)
    public CoverCashFlowFrom() { 
    }

    // Getters and Setters
    public UUID getCashFlowId() {
        return cashFlowId;
    }

    public void setCashFlowId(UUID cashFlowId) {
        this.cashFlowId = cashFlowId;
    }

    public MultipartFile getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(MultipartFile attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    // --- Helper dan Validation Methods ---

    /**
     * Memeriksa apakah file kosong.
     * @return true jika file null atau kosong.
     */
    public boolean isEmpty() {
        return attachmentFile == null || attachmentFile.isEmpty();
    }

    /**
     * Mengambil nama file asli.
     */
    public String getOriginalFilename() {
        return attachmentFile != null ? attachmentFile.getOriginalFilename() : null;
    }

    /**
     * Memeriksa apakah file adalah gambar (untuk bukti kuitansi).
     */
    public boolean isValidImage() {
        if (this.isEmpty()) {
            return false;
        }

        String contentType = attachmentFile.getContentType();
        // Disarankan menggunakan .toLowerCase().equals() untuk robustness, 
        // tetapi di sini mengikuti implementasi asli Anda.
        return contentType != null &&
                (contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("image/webp"));
    }

    /**
     * Memeriksa apakah ukuran file valid (di bawah batas maksimum 5MB).
     */
    public boolean isSizeValid() {
        return attachmentFile != null && attachmentFile.getSize() <= MAX_FILE_SIZE;
    }
}