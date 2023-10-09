package doyu.cocomo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private final int statusCode;
    private final String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(200, "Success", data);
    }

    public static <T> BaseResponse<T> error(int statusCode, String message) {
        return new BaseResponse<>(statusCode, message);
    }
}
