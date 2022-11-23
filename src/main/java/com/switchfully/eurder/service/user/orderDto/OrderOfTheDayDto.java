package com.switchfully.eurder.service.user.orderDto;

import com.switchfully.eurder.domain.users.Address;

public record OrderOfTheDayDto
        (long itemId,
         int amount,
         Address address) {
}

