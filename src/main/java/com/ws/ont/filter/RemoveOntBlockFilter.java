package com.ws.ont.filter;

import com.ws.ont.filter.validation.BaseOntFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class RemoveOntBlockFilter extends BaseOntFilter {

    @Size(max = 100, message = "RemoveBlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String removeBlockReason;
}