package ru.yandex.sbd.messenger.repositories;

import java.util.Optional;

public interface Repository<K, V> {
    void put(K key, V value);

    Optional<V> get(K key);
}
