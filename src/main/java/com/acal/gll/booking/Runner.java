package com.acal.gll.booking;

import com.acal.gll.booking.config.GllBookingConfiguration;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    private final String username;
    private final String password;


    public static void main(String[] args) throws ParseException {

        final Runner r = new Runner(args);
        LOG.info("Gll booking");

        final GenericApplicationContext ctx = new AnnotationConfigApplicationContext(GllBookingConfiguration.class);
        final Booking booking = (Booking) ctx.getBean("booking");
        booking.login(r.username, r.password);
        booking.selectClub("Britannia");
        booking.selectSquashTimetable();
        final DateTime slotDateTime = new DateTime().withYear(2014).withMonthOfYear(5).withDayOfMonth(31).withHourOfDay(11).withMinuteOfHour(15);
        booking.selectSlot(slotDateTime.toDate());
        booking.test();
        booking.quit();
    }


    public Runner(String[] args) throws ParseException {
        final Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("gllUsername").withDescription("GLL username").hasArg().withArgName("gllUsername").create());
        options.addOption(OptionBuilder.withLongOpt("gllPassword").withDescription("GLL password").hasArg().withArgName("gllPassword").create());
        options.addOption("gllPassword", true, "GLL password");
        final CommandLine commandLine = new GnuParser().parse(options, args);
        this.username = commandLine.getOptionValue("gllUsername");
        this.password = commandLine.getOptionValue("gllPassword");

    }


}
