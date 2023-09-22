package uz.javlon.v220.service.websocket.dto;

import lombok.*;
import uz.javlon.v220.service.websocket.WsMethods;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket.dto
 * @since 9/16/2023 3:34 PM
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WsResponseDTO<T> extends WsDTO {

    private Boolean success;
    private String message;
    private T data;
    private Integer code;
    private String error;

    public WsResponseDTO(T data) {
        this.data = data;
        this.success = true;
    }

    public WsResponseDTO(Boolean success, String message, Integer code, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public WsResponseDTO(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static WsResponseDTO withSuccess(Boolean success) {
        var response = new WsResponseDTO<>();
        response.success = success;
        response.message = "OK";
        return response;
    }

    public static WsResponseDTO withMessage(Boolean success, String message) {
        var response = new WsResponseDTO<>();
        response.success = success;
        response.message = message;
        return response;
    }

    public WsResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public WsResponseDTO(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static final WsResponseDTO<Object> SUCCESS = new WsResponseDTO<>(true, "OK", null, null);
    public static final WsResponseDTO<Object> FAIL = new WsResponseDTO<>(false, "FAILED", null, null);
}
