package uz.javlon.v220.service.websocket;

import de.rwth.idsg.ocpp.jaxb.RequestType;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import uz.javlon.v220.service.websocket.collector.CompletableCollector;
import uz.javlon.v220.service.websocket.collector.SessionCollector;
import uz.javlon.v220.service.websocket.dto.WsResponseDTO;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket
 * @since 9/16/2023 2:46 PM
 */
public abstract class AbstractMessagingService<P> implements DefineWsMethod {

    protected final CompletableCollector<RequestType> completableCollector;
    protected final SessionCollector sessionCollector;
    protected final ChargePointServiceFactory chargePointServiceFactory;
    private final ExceptionDirector exceptionDirector = new ExceptionDirector();

    protected AbstractMessagingService(
        CompletableCollector<RequestType> completableCollector,
        SessionCollector sessionCollector,
        ChargePointServiceFactory chargePointServiceFactory
    ) {
        this.completableCollector = completableCollector;
        this.sessionCollector = sessionCollector;
        this.chargePointServiceFactory = chargePointServiceFactory;
    }

    public abstract WsResponseDTO<?> execute(P payload, Principal user);

    protected WsResponseDTO<?> exceptionDirector(Throwable e) {
        return exceptionDirector.director(e);
    }

    public static class ExceptionDirector {

        private final Map<Class<? extends Throwable>, WsResponseDTO<?>> wsResponseMap = new HashMap<>();

        {
            wsResponseMap.put(InterruptedException.class, WsResponseDTO.withMessage(false, "interrupted exception!"));
            wsResponseMap.put(TimeoutException.class, WsResponseDTO.withMessage(false, "timeout exception!"));
            wsResponseMap.put(ExecutionException.class, WsResponseDTO.withMessage(false, "execution exception!"));
            wsResponseMap.put(URISyntaxException.class, WsResponseDTO.withMessage(false, "URI syntax exception exception!"));
        }

        public WsResponseDTO<?> director(Throwable e) {
            return wsResponseMap.get(e.getClass());
        }
    }
}
