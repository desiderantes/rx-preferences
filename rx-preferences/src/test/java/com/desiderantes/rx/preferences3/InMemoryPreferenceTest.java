package com.desiderantes.rx.preferences3;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryPreferenceTest {

    @Test
    public void intPreferenceTests () {
        Preference<Integer> preference = InMemoryPreference.getIntegerPreference("test", 0);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(2);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void floatPreferenceTests () {
        Preference<Float> preference = InMemoryPreference.getFloatPreference("test", 0.0f);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(2.0f);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void doublePreferenceTests () {
        Preference<Double> preference = InMemoryPreference.getDoublePreference("test", 0);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(2.0);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void stringPreferenceTests () {
        Preference<String> preference = InMemoryPreference.getStringPreference("test", "Hello");
        assertThat(preference.isSet()).isFalse();
        preference.setSync("Goodbye");
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void longPreferenceTests () {
        Preference<Long> preference = InMemoryPreference.getLongPreference("test", 0L);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(2L);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void boolPreferenceTests () {
        Preference<Boolean> preference = InMemoryPreference.getBooleanPreference("test", false);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(true);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void enumPreferenceTests () {
        Preference<Roshambo> preference = InMemoryPreference.getEnumPreference("test", Roshambo.PAPER, Roshambo.class);
        assertThat(preference.isSet()).isFalse();
        preference.setSync(Roshambo.SCISSORS);
        assertThat(preference.isSet()).isTrue();
    }

    @Test
    public void objPreferenceTests () {
        Preference<Set<String>> preference = InMemoryPreference.getObjectPreference("test", new HashSet<>());
        assertThat(preference.isSet()).isFalse();
        preference.setSync(Set.of("one", "two"));
        assertThat(preference.isSet()).isTrue();
    }


}
