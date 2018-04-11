package com.zifangdt.ch.ticket.web;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.dto.ticket.input.TicketTypeDto;
import com.zifangdt.ch.ticket.service.ConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/configs")
public class LocalConfigItemController {

    @Autowired
    ConfigItemService configItemService;

    @DeleteMapping("/ticketType/{id}")
    public void deleteTicketType(@PathVariable("id") Long id) {
        configItemService.deleteTicketType(id);
    }

    @PutMapping("/ticketType/{id}")
    public void updateTicketType(@PathVariable("id") Long id, @RequestBody @Valid TicketTypeDto ticketTypeDto) {
        configItemService.updateTicketType(id, ticketTypeDto);
    }

    @GetMapping("/customerServiceStaff")
    public Set<Long> getCustomerServiceStaff() {
        return configItemService.customerServiceStaff();
    }

    @GetMapping("/customerServiceStaff/name")
    public List<Map<String, Object>> getIdAndName() {
        return configItemService.getIdAndNameForCustomerStaff();
    }

    @GetMapping("/ticketTypes")
    public List<IdAndName> ticketTypes(){
        return configItemService.ticketTypes();
    }
}
