package com.desiderantes.rx.preferences3;

import androidx.annotation.NonNull;

import java.util.Optional;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class InMemoryPreference<T> implements Preference<T> {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<T> value;
    private final String key;
    private final T defaultValue;
    private final BehaviorSubject<T> subject;

    public InMemoryPreference(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.subject = BehaviorSubject.createDefault(defaultValue);
    }

    @NonNull
    @Override
    public String key() {
        return key;
    }

    @NonNull
    @Override
    public T defaultValue() {
        return defaultValue;
    }

    @NonNull
    @Override
    public T get() {
        return value.orElse(defaultValue);
    }

    @Override
    public void set(@NonNull T value) {
        this.value = Optional.of(value);
        subject.onNext(value);
    }

    @Override
    public boolean setSync(@NonNull T value) {
        this.value = Optional.of(value);
        return true;
    }

    @Override
    public boolean isSet() {
        return value.isPresent();
    }

    @Override
    public void delete() {
        value = Optional.empty();
        subject.onNext(defaultValue);
    }

    @NonNull
    @Override
    public Observable<T> asObservable() {
        return subject.distinctUntilChanged();
    }

    @NonNull
    @Override
    public Consumer<? super T> asConsumer() {
        return (Consumer<T>) this::set;
    }

    public static <T> Preference<T> getObjectPreference(String key, @NonNull T defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static Preference<String> getStringPreference(String key, @NonNull String defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }
    public static Preference<Boolean> getBooleanPreference(String key, boolean defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static Preference<Integer> getIntegerPreference(String key, int defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static Preference<Long> getLongPreference(String key, long defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static Preference<Float> getFloatPreference(String key, float defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static Preference<Double> getDoublePreference(String key, double defaultValue) {
        return new InMemoryPreference<>(key, defaultValue);
    }

    public static <E extends Enum<E>> Preference<E> getEnumPreference(String key, E defaultValue, Class<E> enumClass) {
        return new Preference<E>() {
            private final InMemoryPreference<String> realPreference = new InMemoryPreference<>(key, defaultValue.name());

            @NonNull
            @Override
            public String key() {
                return realPreference.key();
            }

            @NonNull
            @Override
            public E defaultValue() {
                return Enum.valueOf(enumClass, realPreference.defaultValue());
            }

            @NonNull
            @Override
            public E get() {
                return Enum.valueOf(enumClass, realPreference.get());
            }

            @Override
            public void set(@NonNull E value) {
                realPreference.set(value.name());
            }

            @Override
            public boolean setSync(@NonNull  E value) {
                return realPreference.setSync(value.name());
            }

            @Override
            public boolean isSet() {
                return realPreference.isSet();
            }

            @Override
            public void delete() {
                realPreference.delete();
            }

            @NonNull
            @Override
            public Observable<E> asObservable() {
                return realPreference.asObservable().map(it -> Enum.valueOf(enumClass, it));
            }

            @NonNull
            @Override
            public Consumer<? super E> asConsumer() {
                return (Consumer<E>) this::set;
            }
        };
    }
}
