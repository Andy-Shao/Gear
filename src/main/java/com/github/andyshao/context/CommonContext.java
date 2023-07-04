package com.github.andyshao.context;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 11, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface CommonContext extends Map<String, Object> {
    /**
     * Set value by key name
     * @param key {@link ContextKey}
     * @param value the value which should be setted
     * @return the previous value of the {@link CommonContext}
     * @param <T> data type
     */
    @SuppressWarnings("unchecked")
    default <T> T setByKey(ContextKey<T> key, T value) {
        return (T) put(key.keyName() , value);
    }

    /**
     * Set if the key is absent, at same time
     * the new value will be set in the {@link CommonContext}
     * @param key {@link ContextKey}
     * @param function the value generation {@link Function}
     * @return the previous value
     * @param <T> data type
     */
    @SuppressWarnings("unchecked")
    default <T> T computeIfAbsent(ContextKey<T> key, Function<ContextKey<T> , T> function) {
        return (T) computeIfAbsent(key.keyName() , k -> function.apply(key));
    }

    /**
     * Does {@link CommonContext} contains the key
     * @param key {@link ContextKey}
     * @return if the {@link CommonContext} contains the key,
     * then return true. Else return false.
     * @param <T> data type
     */
    default <T> boolean containsKey(ContextKey<T> key) {
        return containsKey(key.keyName());
    }

    /**
     * Get value by {@link ContextKey}
     * @param key {@link ContextKey}
     * @return the value which is stored in {@link CommonContext}
     * @param <T> data type
     */
    @SuppressWarnings("unchecked")
    default <T> T getByKey(ContextKey<T> key) {
        return (T) get(key.keyName());
    }

    /**
     * Get value by key.
     * If the key does not exist in the {@link CommonContext},
     * then return the default value
     * @param key {@link ContextKey}
     * @param defaultValue the default value
     * @return the value
     * @param <T> data type
     */
    default <T> T getOrDefaultByKey(ContextKey<T> key, T defaultValue) {
        return getOpByKey(key)
                .orElse(defaultValue);
    }

    /**
     * Get value by key.
     * If the key does not exist, then throw exception
     * @param key {@link ContextKey}
     * @param exception the exception which maybe threw
     * @return the value
     * @param <T> the data type
     * @param <X> the exception type
     * @throws X the exception
     */
    default <T, X extends Throwable> T getOrThrow(ContextKey<T> key, Supplier<? extends X> exception) throws X {
        return getOpByKey(key)
                .<X>orElseThrow(exception);
    }

    /**
     * Get the value by key.
     * If the key does not exist, then generate one.
     * But the generated value does not be stored in the {@link CommonContext}
     * @param key {@link ContextKey}
     * @param function the value generation function
     * @return the value
     * @param <T> data type
     */
    default <T> T computeIfAbsence(ContextKey<T> key, Function<ContextKey<T>, T> function) {
    	return getOpByKey(key)
                .orElseGet(() -> function.apply(key));
    }

    /**
     * Get a optional value
     * @param key {@link ContextKey}
     * @return a optional value
     * @param <T> data type
     */
    default <T> Optional<T> getOpByKey(ContextKey<T> key) {
    	return Optional.ofNullable(getByKey(key));
    }
}
