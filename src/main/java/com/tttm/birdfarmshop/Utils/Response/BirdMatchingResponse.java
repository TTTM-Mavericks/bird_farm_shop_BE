package com.tttm.birdfarmshop.Utils.Response;

import com.tttm.birdfarmshop.Enums.BirdColor;
import com.tttm.birdfarmshop.Enums.BirdMatchingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BirdMatchingResponse {
    private Float successRate;
    private BirdColor color;
    private String size;
    private BirdMatchingStatus status;
}
