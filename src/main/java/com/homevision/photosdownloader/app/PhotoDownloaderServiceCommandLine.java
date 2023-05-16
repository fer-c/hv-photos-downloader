package com.homevision.photosdownloader.app;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Profile("!test")
@Slf4j
@Component
public class PhotoDownloaderServiceCommandLine implements CommandLineRunner {
    private static final String OPTION_PAGE_SIZE = "s";
    private static final String OPTION_PAGES = "n";
    private static final String OPTION_URL = "u";
    private static final String OPTION_HELP = "help";
    private static final String OPTION_DIR = "d";

    private final PhotoDownloaderService service;

    @Value("${app.defaultBaseUrl}")
    private String defaultBaseUrl;

    @Value("${app.defaultMaxPages}")
    private Integer defaultMaxPages;

    @Value("${app.defaultPageSize}")
    private Integer defaultPageSize;

    @Value("${app.defaultTargetPath}")
    private String defaultTargetPath;

    @Value("${app.name}")
    private String appName;

    public PhotoDownloaderServiceCommandLine(PhotoDownloaderService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        var options = parseCommandLineArguments(args);
        var parser = new DefaultParser();
        var cmd = parser.parse(options, args);
        if (cmd.hasOption(OPTION_HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.setWidth(125);
            formatter.printHelp(appName, options, true);
        } else {
            var targetPath = cmd.getOptionValue(OPTION_DIR, defaultTargetPath);
            File targetDir = new File(targetPath);
            if (!targetDir.exists() || !targetDir.isDirectory()) {
                System.err.println("The requested destination does not exists");
            } else {
                var baseUrl = cmd.getOptionValue(OPTION_URL, defaultBaseUrl);
                log.info("baseUrl: {}", baseUrl);
                var pageSize = optionValueInteger(cmd, OPTION_PAGE_SIZE, defaultPageSize);
                log.info("pageSize: {}", pageSize);
                var pages = optionValueInteger(cmd, OPTION_PAGES, defaultMaxPages);
                log.info("pages: {}", pages);
                log.info("targetDir: {}", targetDir);
                service.downloadPhotos(baseUrl, pages, pageSize, targetDir);
            }
        }
    }

    private Integer optionValueInteger(CommandLine cmd, String opt, Integer defaultValue) {
        String value = cmd.getOptionValue(opt);
        if (value == null) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    private Options parseCommandLineArguments(String... args) throws ParseException {
        final Options options = new Options();
        options.addOption(
                new Option(OPTION_URL, "url", true, "Base URL for the download API. Default %s".formatted(defaultBaseUrl)));
        options.addOption(
                new Option(OPTION_PAGES, "pages", true, "Number of pages to download. Default %s".formatted(defaultMaxPages)));
        options.addOption(
                new Option(OPTION_PAGE_SIZE, "size", true, "Size of pages to download. Default %s".formatted(defaultPageSize)));
        options.addOption(new Option(OPTION_DIR, "dest", true, "Download directory. Default %s".formatted(defaultTargetPath)));
        options.addOption(new Option(OPTION_HELP, "Show Help"));
        return options;
    }

}
