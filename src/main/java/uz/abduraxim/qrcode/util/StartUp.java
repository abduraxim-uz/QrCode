package uz.abduraxim.qrcode.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import uz.abduraxim.MyLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartUp {

    private final Environment env;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        try {
            Thread.sleep(3000);
            String port = getServerPort();
            log.info("\n\n" +
                    "===================================================\n" +
                    "QcCode Generator is running\n" +
                    "Click the url: http://localhost:{}/qr/start\n" +
                    "Started at: {}", port, LocalDateTime.now().format(formatter) +
                    "\n===================================================" +
                    "\n\n");
        } catch (Exception e) {
            MyLogger.warn(e.getMessage());
        }
    }

    public String getServerPort() {
        return env.getProperty("server.port", "8080");
    }
}
