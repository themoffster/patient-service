package uk.com.poodle.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Relation {

    PARENT(1L),
    GRANDPARENT(2L),
    OTHER(3L);

    private final Long id;
}
