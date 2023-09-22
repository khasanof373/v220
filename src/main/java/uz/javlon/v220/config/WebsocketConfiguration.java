package uz.javlon.v220.config;

import com.google.common.collect.Lists;
import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import tech.jhipster.config.JHipsterProperties;
import uz.javlon.v220.ocpp.service.ChargePointHelperService;
import uz.javlon.v220.ocpp.ws.OcppWebSocketHandshakeHandler;
import uz.javlon.v220.ocpp.ws.ocpp12.Ocpp12WebSocketEndpoint;
import uz.javlon.v220.ocpp.ws.ocpp15.Ocpp15WebSocketEndpoint;
import uz.javlon.v220.ocpp.ws.ocpp16.Ocpp16WebSocketEndpoint;
import uz.javlon.v220.security.AuthoritiesConstants;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final long PING_INTERVAL = TimeUnit.MINUTES.toMinutes(15);
    public static final Duration IDLE_TIMEOUT = Duration.ofHours(2);
    public static final int MAX_MSG_SIZE = 8_388_608; // 8 MB for max message size

    private final JHipsterProperties jHipsterProperties;

    @Autowired
    private ChargePointHelperService chargePointHelperService;

    //    @Autowired
    //    private Ocpp12WebSocketEndpoint ocpp12WebSocketEndpoint;
    //    @Autowired
    //    private Ocpp15WebSocketEndpoint ocpp15WebSocketEndpoint;
    @Autowired
    private Ocpp16WebSocketEndpoint ocpp16WebSocketEndpoint;

    public WebsocketConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowedOrigins = Optional
            .ofNullable(jHipsterProperties.getCors().getAllowedOrigins())
            .map(origins -> origins.toArray(new String[0]))
            .orElse(new String[0]);
        registry
            .addEndpoint("/websocket/tracker")
            .setHandshakeHandler(defaultHandshakeHandler())
            .setAllowedOrigins(allowedOrigins)
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes
            ) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
                }
                return true;
            }

            @Override
            public void afterHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Exception exception
            ) {}
        };
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                Principal principal = request.getPrincipal();
                if (principal == null) {
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
                    principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
                }
                return principal;
            }
        };
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        OcppWebSocketHandshakeHandler handshakeHandler = new OcppWebSocketHandshakeHandler(
            new DefaultHandshakeHandler(),
            Lists.newArrayList(
                ocpp16WebSocketEndpoint
                //                , ocpp15WebSocketEndpoint, ocpp12WebSocketEndpoint
            ),
            chargePointHelperService
        );

        registry
            .addHandler(handshakeHandler.getDummyWebSocketHandler(), "/websocket/CentralSystemService/*")
            .setHandshakeHandler(handshakeHandler)
            .setAllowedOrigins("*");
    }
}
