package com.tttm.birdfarmshop.Utils.Request;

import com.tttm.birdfarmshop.Enums.BirdColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BirdMatchingRequest {
    BirdRequest firstBird;
    BirdRequest secondBird;
}
