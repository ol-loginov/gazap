package waypalm.common.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class JaxbJacksonObjectMapper extends ObjectMapper {
    public JaxbJacksonObjectMapper() {
        getJsonFactory().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        getJsonFactory().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        setDeserializationConfig(configure(getDeserializationConfig()));
        setSerializationConfig(configure(getSerializationConfig()));
    }

    private SerializationConfig configure(SerializationConfig config) {
        return config
                .withAnnotationIntrospector(new JaxbAnnotationIntrospector())
                .with(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING)
                .without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    private DeserializationConfig configure(DeserializationConfig config) {
        return config
                .withAnnotationIntrospector(new JaxbAnnotationIntrospector())
                .with(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
