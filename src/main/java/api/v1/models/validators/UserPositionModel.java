package api.v1.models.validators;

import api.v1.handlers.TextResources;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class UserPositionModel {

    @NotNull(message = TextResources.X_AXIS_NOT_VALID)
    @Digits(integer = 10,fraction = 0,message = TextResources.X_AXIS_NOT_VALID)
    private Integer xAxis;

    @NotNull(message = TextResources.Y_AXIS_NOT_VALID)
    @Digits(integer = 10, fraction = 0, message = TextResources.Y_AXIS_NOT_VALID)
    private Integer yAxis;

    public Integer getxAxis() {
        return xAxis;
    }

    public void setxAxis(Integer xAxis) {
        this.xAxis = xAxis;
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }
}
