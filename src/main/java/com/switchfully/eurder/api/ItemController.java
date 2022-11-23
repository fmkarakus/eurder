package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.StockDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    private final SecurityService securityService;

    public ItemController(ItemService itemService, SecurityService securityService) {
        this.itemService = itemService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addNewItem(@RequestHeader String authorization, @Valid @RequestBody CreateItemDto newItem) {
        securityService.validateAuthorization(authorization, Feature.ADD_NEW_ITEM);
        return itemService.addNewItem(newItem);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getListOfItems(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_ALL_ITEMS);
        return itemService.getAllItems();
    }

    @PatchMapping(path = "{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto updateItem(@RequestHeader String authorization, @PathVariable long itemId, @RequestBody UpdateItemDto updatedItem) {
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEMS);
        return itemService.updateItem(itemId, updatedItem);
    }

    @GetMapping(path = "stock",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> getStockStatus(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_STOCK_STATUS);
        return itemService.getStockStatus();
    }

    @GetMapping(path = "stock",params ={"status"} ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> getStockStatusFiltered(@RequestHeader String authorization,@RequestParam(required = false) StockStatus status) {
        securityService.validateAuthorization(authorization, Feature.VIEW_STOCK_STATUS);
        return itemService.getStockStatusFilteredBy(status);
    }
}
