package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CashFlowFormTests {

    @Test
    void testCashFlowFormGettersAndSetters() {
        // 1. Data Uji
        UUID testId = UUID.randomUUID();
        String type = "Pemasukan";
        String source = "Gaji Utama";
        String label = "Investasi Jangka Panjang";
        Integer amount = 5000000;
        String description = "Transfer gaji bulan ini";

        // 2. Buat instance dan set nilai
        CashFlowForm form = new CashFlowForm();
        form.setId(testId);
        form.setType(type);
        form.setSource(source);
        form.setLabel(label);
        form.setAmount(amount);
        form.setDescription(description);

        // 3. Verifikasi nilai melalui getters
        assertNotNull(form);
        assertEquals(testId, form.getId());
        assertEquals(type, form.getType());
        assertEquals(source, form.getSource());
        assertEquals(label, form.getLabel());
        assertEquals(amount, form.getAmount());
        assertEquals(description, form.getDescription());
    }

    @Test
    void testCashFlowFormDefaultConstructor() {
        CashFlowForm form = new CashFlowForm();
        assertNotNull(form);
    }
}