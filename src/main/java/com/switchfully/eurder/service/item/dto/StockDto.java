package com.switchfully.eurder.service.item.dto;

import com.switchfully.eurder.domain.item.StockStatus;

public record StockDto(long itemId, StockStatus stockStatus) {
}
