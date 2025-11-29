package org.delcom.app.dto;

import java.util.UUID;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CashFlowForm {

    private UUID id; // Hanya diperlukan saat UPDATE

    @NotBlank(message = "Tipe harus diisi (Pemasukan/Pengeluaran)")
    private String type; // Contoh: "Pemasukan" atau "Pengeluaran"

    @NotBlank(message = "Sumber tidak boleh kosong")
    @Size(max = 100, message = "Sumber maksimal 100 karakter")
    private String source; // Contoh: "Gaji", "Transfer", "Belanja"

    @NotBlank(message = "Label tidak boleh kosong")
    @Size(max = 50, message = "Label maksimal 50 karakter")
    private String label; // Contoh: "Utama", "Sekunder", "Investasi"

    @NotNull(message = "Jumlah (Amount) tidak boleh kosong")
    @DecimalMin(value = "1.0", message = "Jumlah harus lebih dari 0")
    private Integer amount; // Jumlah transaksi

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    @Size(max = 255, message = "Deskripsi maksimal 255 karakter")
    private String description; // Detail deskripsi transaksi

    // Constructor
    public CashFlowForm() {
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}