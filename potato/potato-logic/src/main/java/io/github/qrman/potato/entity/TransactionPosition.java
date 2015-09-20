package io.github.qrman.potato.entity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPosition {

    private long loads;
    private BigDecimal price;

}
