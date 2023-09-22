package uz.javlon.v220.service.websocket;

/**
 * @author Nurislom
 * @see uz.javlon.v220.service.websocket
 * @since 9/15/2023 6:28 PM
 */
public class WebSocketConstants {

    public static final String TOPIC = "/topic/messages";
    public static final String SUBSCRIBE_TOPIC = "/topic/ocpp/updates";
    public static final String USER_TOPIC = "/user/topic/updates";
    public static final String WS_URL_START = "ws://localhost:";
    public static final String OCPP_URL = "/websocket/CentralSystemService/";
    public static final String SIMULATE_URL = "/websocket/CentralSystemService/123";

    public static String ocppUrl(String chargeBoxId, Integer serverPort) {
        return WS_URL_START.concat(String.valueOf(serverPort)) + OCPP_URL.concat(chargeBoxId);
    }
}
