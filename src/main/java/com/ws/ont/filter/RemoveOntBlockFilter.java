package com.ws.ont.filter;

import com.ws.ont.filter.validation.BaseOntFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class RemoveOntBlockFilter extends BaseOntFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 50 characters")
    @NotEmpty(message = "Login is mandatory")
    private String login;

    @Size(max = 100, message = "RemoveBlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String removeBlockReason;
}