package vn.io.vutiendat3601.wikimedia.consumer;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHost;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import vn.io.vutiendat3601.wikimedia.producer.WikimediaChangeProducer;

@Slf4j
public class OpenSearchConsumer {
  private static final String WIKIMEDIA_INDEX = "wikimedia";
  private static final URI openSearchUri = URI.create("http://localhost:9200");

  public static void main(String[] args) throws IOException {
    // # Create OpenSearch client
    var openSearchClient = createOpenSearchClient();
    // try (openSearchClient) {
    var getIndexReq = new GetIndexRequest(WIKIMEDIA_INDEX);
    if (openSearchClient.indices().exists(getIndexReq, RequestOptions.DEFAULT)) {
      log.info("The {} index has already been created.", WIKIMEDIA_INDEX);
    } else {
      var createIndexReq = new CreateIndexRequest(WIKIMEDIA_INDEX);
      openSearchClient.indices().create(createIndexReq, RequestOptions.DEFAULT);
      log.info("The wikimedia OpenSearch index has been created.");
    }
    // }

    // # Create Kafka client
    var kafkaConsumer = createKafkaConsumer();
    kafkaConsumer.subscribe(Arrays.asList(WikimediaChangeProducer.TOPIC));

    // # Logic
    while (true) {
      var records = kafkaConsumer.poll(Duration.ofSeconds(1));
      var numOfRecords = records.count();
      log.info("### Received number of records: {} ###", numOfRecords);
      var bulkReq = new BulkRequest();
      for (var record : records) {
        // var id = "%s_%d_%s".formatted(record.topic(), record.partition(), record.offset());
        var id = extractId(record.value());

        var indexReq =
            new IndexRequest(WIKIMEDIA_INDEX).source(record.value(), XContentType.JSON).id(id);
        bulkReq.add(indexReq);
        // var indexResp = openSearchClient.index(indexReq, RequestOptions.DEFAULT);

      }
      if (bulkReq.numberOfActions() > 0) {
        var bulkResp = openSearchClient.bulk(bulkReq, RequestOptions.DEFAULT);
        log.info("Inserted " + bulkResp.getItems().length + " record(s).");
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        kafkaConsumer.commitAsync();
        log.info("Offsets have been committed.");
      }
    }

    // close things
  }

  private static RestHighLevelClient createOpenSearchClient() {
    var restClient =
        RestClient.builder(
            new HttpHost(
                openSearchUri.getScheme(), openSearchUri.getHost(), openSearchUri.getPort()));
    return new RestHighLevelClient(restClient);
  }

  private static KafkaConsumer<String, String> createKafkaConsumer() {
    var groupId = "kafkaopensearch";

    var props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("key.deserializer", StringDeserializer.class.getName());
    props.setProperty("value.deserializer", StringDeserializer.class.getName());
    props.setProperty("group.id", groupId);
    props.setProperty("auto.offset.reset", "latest");
    props.setProperty("enable.auto.commit", "false");

    return new KafkaConsumer<>(props);
  }

  private static String extractId(String json) {
    return JsonParser.parseString(json)
        .getAsJsonObject()
        .get("meta")
        .getAsJsonObject()
        .get("id")
        .getAsString();
  }
}
