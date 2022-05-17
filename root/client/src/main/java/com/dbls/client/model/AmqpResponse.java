package com.dbls.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AmqpResponse {

    private String name;
    private List<String> names;

}
