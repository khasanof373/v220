package uz.javlon.v220.service.websocket.dto;

import javax.validation.constraints.NotNull;
import lombok.*;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket.dto
 * @since 9/17/2023 11:11 AM
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WsRequestDTO<T> extends WsDTO {

    @NotNull
    private T payload;
}
