package com.mycompany.springeci;

import java.util.concurrent.atomic.AtomicLong;

public class GreetingController {
    private static final String template = "hello, %s";
    private final AtomicLong counter = new AtomicLong();

}