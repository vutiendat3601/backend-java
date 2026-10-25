package vn.io.vutiendat3601.javaconcurrency.performance.httpserverperformance;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServerPerformanceApplication {
  private static final String INPUT_FILE = "throughput/war-and-peace.txt";
  // private static final int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors();
  private static final int NUM_OF_THREADS = 10;
  private static String TEXT = "";

  static {
    var fileInpStream = ClassLoader.getSystemResourceAsStream(INPUT_FILE);
    try {
      TEXT = new String(fileInpStream.readAllBytes());
    } catch (IOException e) {
      log.error("Error when reading file content: {}", INPUT_FILE, e);
    }
  }

  public static void main() throws IOException {
    log.info("Number of threads is {}.", NUM_OF_THREADS);
    startServer();
  }

  public static void startServer() throws IOException {
    final var PORT = 8000;
    var server = HttpServer.create(new InetSocketAddress(PORT), 0);
    server.createContext("/search", new WordCountHandler(TEXT));
    var executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
    server.setExecutor(executor);
    server.start();
    log.info(
        "Server is running at port {}, number of threads in thread pool is {}",
        PORT,
        NUM_OF_THREADS);
  }
}
