package com.github.andyshao.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 4, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface MessageFactory {

    /**
     * build {@link MessageContext}
     * 
     * @return {@link MessageContext}
     */
    public default MessageContext buildMessageContext() {
        MessageContext context = new MessageContext() {
            private final Map<String , Object> map = new HashMap<>();

            @Override
            public void clear() {
                this.map.clear();
            }

            @Override
            public boolean containsKey(Object key) {
                return this.map.containsKey(key);
            }

            @Override
            public boolean containsValue(Object value) {
                return this.map.containsValue(value);
            }

            @Override
            public Set<Entry<String , Object>> entrySet() {
                return this.map.entrySet();
            }

            @Override
            public Object get(Object key) {
                return this.map.get(key);
            }

            @Override
            public boolean isEmpty() {
                return this.map.isEmpty();
            }

            @Override
            public Set<String> keySet() {
                return this.map.keySet();
            }

            @Override
            public Object put(String key , Object value) {
                return this.map.put(key , value);
            }

            @Override
            public void putAll(Map<? extends String , ? extends Object> m) {
                this.map.putAll(m);
            }

            @Override
            public Object remove(Object key) {
                return this.map.remove(key);
            }

            @Override
            public int size() {
                return this.map.size();
            }

            @Override
            public Collection<Object> values() {
                return this.map.values();
            }
        };
        context.cleanStatus();
        return context;
    }

    /**
     * build {@link MessageDecoder}
     * 
     * @param context {@link MessageContext}
     * @return {@link MessageDecoder}
     */
    public MessageDecoder buildMessageDecoder(MessageContext context);

    /**
     * build {@link MessageEncoder}
     * 
     * @param context {@link MessageContext}
     * @return {@link MessageEncoder}
     */
    public MessageEncoder buildMessageEncoder(MessageContext context);

    /**
     * build {@link MessageProcess}
     * 
     * @param context {@link MessageContext}
     * @return {@link MessageProcess}
     */
    public MessageProcess buildMessageProcess(MessageContext context);
}
