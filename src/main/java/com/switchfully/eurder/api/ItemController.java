package com.switchfully.eurder.api;

import com.switchfully.eurder.api.dtos.CreateItemDto;
import com.switchfully.eurder.api.dtos.ItemDto;
import com.switchfully.eurder.api.dtos.UpdateItemDto;
import com.switchfully.eurder.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addNewItem(@RequestHeader String authorization, @Valid @RequestBody CreateItemDto newItem) {
        return itemService.addNewItem(authorization, newItem);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getListOfItems(@RequestHeader String authorization) {
        return itemService.getAllItems(authorization);
    }

    @PatchMapping(path = "{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto updateItem(@RequestHeader String authorization, @PathVariable long itemId, @RequestBody UpdateItemDto updatedItem) {
        return itemService.updateItem(authorization, itemId, updatedItem);
    }
}
