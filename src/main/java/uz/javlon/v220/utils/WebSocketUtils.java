package uz.javlon.v220.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.messaging.simp.stomp.StompHeaders;

/**
 * @author Nurislom
 * @see uz.javlon.v220.utils
 * @since 9/15/2023 5:42 PM
 */
public abstract class WebSocketUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper() {
        {
            registerModule(new JavaTimeModule());
        }
    };

    public static String randomMsgID() {
        return RandomStringUtils.random(10, true, true);
    }

    public static boolean equalsType(Class<?> payloadType, Class<?> methodParamType) {
        return payloadType.equals(methodParamType);
    }

    public static <T> T convertPayloadToObject(Object payload, Class<T> methodParamType) {
        return objectMapper.convertValue(payload, methodParamType);
    }

    public static StompHeaders createStompHeader() {
        StompHeaders headers = new StompHeaders();
        headers.setMessageId(randomMsgID());
        headers.setDestination("/test");
        return headers;
    }
}
