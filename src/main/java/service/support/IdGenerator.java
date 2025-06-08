package service.support;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Потокобезопасный генератор уникальных числовых ID.
 */
public class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger(0);

    public int nextId() {
        return counter.incrementAndGet();
    }
}
