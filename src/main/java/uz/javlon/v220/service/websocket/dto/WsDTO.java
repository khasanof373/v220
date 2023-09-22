package uz.javlon.v220.service.websocket.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import uz.javlon.v220.service.websocket.WsMethods;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket.dto
 * @since 9/17/2023 11:13 AM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class WsDTO {

    @NotNull
    private WsMethods method;

    @NotBlank
    private String id;
}
