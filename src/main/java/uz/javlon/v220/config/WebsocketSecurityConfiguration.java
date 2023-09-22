package uz.javlon.v220.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import uz.javlon.v220.security.AuthoritiesConstants;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpDestMatchers("/websocket/CentralSystemService/**")
            .permitAll()
            .nullDestMatcher()
            .authenticated()
            .simpDestMatchers("/topic/tracker")
            .hasAuthority(AuthoritiesConstants.ADMIN)
            // matches any destination that starts with /topic/
            // (i.e. cannot send messages directly to /topic/)
            // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
            // /topic/messages-user<id>)
            .simpDestMatchers("/app/handler")
            .authenticated()
            .simpDestMatchers("/topic/**")
            .authenticated()
            // message types other than MESSAGE and SUBSCRIBE
            .simpTypeMatchers(SimpMessageType.SUBSCRIBE, SimpMessageType.OTHER)
            .permitAll()
            // catch all
            .anyMessage()
            .denyAll();
    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
