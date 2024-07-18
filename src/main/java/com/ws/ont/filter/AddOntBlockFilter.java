package com.ws.ont.filter;

import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddOntBlockFilter extends BaseCvlanFilter {

    @Size(max = 100, message = "BlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String blockReason;

}
