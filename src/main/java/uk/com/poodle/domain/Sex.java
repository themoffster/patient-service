package uk.com.poodle.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Sex {

    MALE("M"),
    FEMALE("F");

    private final String code;

    public static Sex fromCode(String code) {
        return Stream.of(Sex.values())
            .filter(statusCategory -> Objects.equals(statusCategory.getCode(), code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Sex '%s' not found.", code)));
    }
}
