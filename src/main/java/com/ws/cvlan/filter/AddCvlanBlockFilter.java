package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddCvlanBlockFilter extends BaseCvlanFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 50 characters")
    @NotEmpty(message = "Login is mandatory")
    private String login;

    @NotNull(message = "Svlan is mandatory")
    private Integer svlan;

    @NotNull(message = "Cvlan is mandatory")
    private Integer cvlan;

    @Size(max = 100, message = "BlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String blockReason;
}
