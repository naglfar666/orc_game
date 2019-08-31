package api.v1.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashMap;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {

    @NonNull String type;

    String text;

    T data;

    public BaseResponse(@NonNull String type) {
        this.type = type;
    }

    public BaseResponse(@NonNull String type, String text) {
        this.type = type;
        this.text = text;
    }

    public BaseResponse(@NonNull String type, String text, T data) {
        this.type = type;
        this.text = text;
        this.data = data;
    }
}
