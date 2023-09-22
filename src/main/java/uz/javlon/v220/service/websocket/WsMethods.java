package uz.javlon.v220.service.websocket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.javlon.v220.ocpp.param.RemoteStartTransactionParams;
import uz.javlon.v220.ocpp.param.RemoteStopTransactionParams;
import uz.javlon.v220.service.websocket.dto.OpenConnectionDTO;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket
 * @since 9/16/2023 3:38 PM
 */
@Getter
@RequiredArgsConstructor
public enum WsMethods {
    OPEN_CONNECTION(OpenConnectionDTO.class),
    START_TRANSACTION(RemoteStartTransactionParams.class),
    STOP_TRANSACTION(RemoteStopTransactionParams.class);

    private final Class<?> dtoType;
}
