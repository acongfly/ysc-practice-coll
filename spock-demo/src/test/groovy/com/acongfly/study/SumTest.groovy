package com.acongfly.study

import spock.lang.Specification

class SumTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }
    def sum = new Sum();

    def "sum should return param1+param2"() {
        expect:
        sum.sum(1, 1) == 2
    }

    def "Sum"() {
    }
}
