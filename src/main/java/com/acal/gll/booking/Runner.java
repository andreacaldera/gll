package com.acal.gll.booking;

import com.acal.gll.booking.config.GllBookingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {

        LOG.info("Gll booking");

        final GenericApplicationContext ctx = new AnnotationConfigApplicationContext(GllBookingConfiguration.class);
        final Booking booking = (Booking) ctx.getBean("booking");
        booking.login();
        booking.quit();

    }

}
