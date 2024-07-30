package com.ws.ont.filter;

import com.ws.ont.filter.validation.BaseOntFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddOntBlockFilter extends BaseOntFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    @ApiModelProperty(value = "System origin", required = true, example = "FB")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 50 characters")
    @NotEmpty(message = "Login is mandatory")
    @ApiModelProperty(value = "Login", required = true, example = "netwin")
    private String login;

    @Size(max = 100, message = "BlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    @ApiModelProperty(value = "Block reason", required = true, example = "Ocupado Gerência")
    private String blockReason;
}