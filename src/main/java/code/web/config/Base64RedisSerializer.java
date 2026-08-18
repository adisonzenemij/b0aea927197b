package code.web.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Optional Base64 envelope for Redis values, equivalent to the reference project's format switch.
 */
final class Base64RedisSerializer implements RedisSerializer<Object> {
  private final RedisSerializer<Object> delegate;

  Base64RedisSerializer(RedisSerializer<Object> delegate) {
    this.delegate = delegate;
  }

  @Override
  public byte[] serialize(Object value) throws SerializationException {
    byte[] bytes = delegate.serialize(value);
    return bytes == null ? null : Base64.getEncoder().encode(bytes);
  }

  @Override
  public Object deserialize(byte[] bytes) throws SerializationException {
    return bytes == null
        ? null
        : delegate.deserialize(
            Base64.getDecoder().decode(new String(bytes, StandardCharsets.UTF_8)));
  }
}
