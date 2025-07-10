package com.csys.template.AI_Search_Box;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiQueryResponse {
    private String entityType;
    private Map<String, String> entities;
    private String doumean;
    private String  error;

   
}