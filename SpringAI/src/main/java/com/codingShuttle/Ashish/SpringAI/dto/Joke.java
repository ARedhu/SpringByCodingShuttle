package com.codingShuttle.Ashish.SpringAI.dto;

public record Joke(
        String text,
        String category,
        Double laughScore,
        Boolean isNSFW // is Not Safe For Work
) {
}

// Record is a special type of java class used to store data with less boilerplate code. As Java automatically generates the constructor, accessor methods, equals(), hashCode(), and toString().
/*Records in Java are shallowly immutable, not completely immutable.
        - Fields: Cannot be reassigned after initialization because they are final.
        - Referenced objects: Can still be modified if they are mutable.
        */