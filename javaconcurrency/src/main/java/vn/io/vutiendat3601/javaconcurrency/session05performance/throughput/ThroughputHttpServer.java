package vn.io.vutiendat3601.javaconcurrency.session05performance.throughput;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThroughputHttpServer {
  private static final String INPUT_FILE = "throughput/war-and-peace.txt";
  private static final int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors();

  public static void main(String[] args) throws IOException {
    log.info("Number of threads is {}.", NUM_OF_THREADS);
    var fileInpStream = ClassLoader.getSystemResourceAsStream(INPUT_FILE);
    var text = new String(fileInpStream.readAllBytes());
    startServer(text);
  }

  public static void startServer(String text) throws IOException {
    var server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/search", new WordCountHandler(text));
    var executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
    server.setExecutor(executor);
    server.start();
  }
}
