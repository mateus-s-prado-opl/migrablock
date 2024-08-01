package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class RemoveCvlanBlockFilter extends BaseCvlanFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    @ApiModelProperty(value = "System origin", required = true, example = "SystemX")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 50 characters")
    @NotEmpty(message = "Login is mandatory")
    @ApiModelProperty(value = "User login", required = true, example = "user123")
    private String login;

    @Size(max = 100, message = "RemoveBlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "RemoveBlockReason is mandatory")
    @ApiModelProperty(value = "Reason for removing the block", required = true, example = "No longer needed")
    private String removeBlockReason;
}