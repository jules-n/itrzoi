package com.nure.br4.domain.dto;

import com.nure.br4.domain.models.OrderStatus;
import com.nure.br4.domain.models.Position;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderPostDTO {
    private int place;
    private List<Position> positions;
    private OrderStatus status;
}
