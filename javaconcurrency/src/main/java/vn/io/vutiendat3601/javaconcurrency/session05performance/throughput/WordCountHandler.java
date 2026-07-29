package vn.io.vutiendat3601.javaconcurrency.session05performance.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class WordCountHandler implements HttpHandler {
  private final String text;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    var query = exchange.getRequestURI().getQuery();
    var keyValue = query.split("=");
    var action = keyValue[0];
    var word = keyValue[1];
    if (!action.equals("word")) {
      exchange.sendResponseHeaders(400, 0);
      return;
    }
    var count = countWord(word);

    var resp = Long.toString(count).getBytes();
    exchange.sendResponseHeaders(200, resp.length);
    var outStream = exchange.getResponseBody();
    outStream.write(resp);
    outStream.close();
  }

  public long countWord(String word) {
    var count = 0L;
    int index = 0;
    while (index >= 0) {
      index = text.indexOf(word, index);
      if (index >= 0) {
        count++;
        index++;
      }
    }
    log.info("Counted word '{}': {}", word, count);
    return count;
  }
}
