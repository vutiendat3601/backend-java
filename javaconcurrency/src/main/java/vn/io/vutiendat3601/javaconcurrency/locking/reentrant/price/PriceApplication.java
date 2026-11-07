package vn.io.vutiendat3601.javaconcurrency.locking.reentrant.price;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceApplication extends Application {
  private static final String JAVA_FX_THREAD_NAME = "JavaFX Application Thread";

  public static void main(String[] args) {

    new Thread(
            () -> {
              while (true) {
                Thread.getAllStackTraces().keySet().stream()
                    .filter(thread -> JAVA_FX_THREAD_NAME.equals(thread.getName()))
                    .findFirst()
                    .ifPresent(
                        javaFXThread ->
                            log.info("State of {}: {}", javaFXThread, javaFXThread.getState()));

                try {
                  Thread.sleep(100);
                } catch (InterruptedException e) {
                }
              }
            })
        .start();
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Cryptocurrency Prices");

    var grid = createGrid();
    var cryptoLabels = createCryptoPriceLabels();

    addLabelsToGrid(cryptoLabels, grid);

    var width = 300D;
    var height = 250D;

    var root = new StackPane();

    var background = createBackgroundRectangleWithAnimation(width, height);

    root.getChildren().add(background);
    root.getChildren().add(grid);

    primaryStage.setScene(new Scene(root, width, height));

    var pricesContainer = new PriceContainer();

    var priceUpdater = new PriceUpdater(pricesContainer);

    var animationTimer =
        new AnimationTimer() {
          @Override
          public void handle(long now) {

            // pricesContainer.getLock().lock();
            // Change to this type of lock instead of tryLock. The JavaFX Thread will be blocked to
            // aquiring lock.

            if (pricesContainer.getLock().tryLock()) {
              try {
                var bitcoinLabel = cryptoLabels.get("BTC");
                bitcoinLabel.setText(String.valueOf(pricesContainer.getBitcoinPrice()));

                var etherLabel = cryptoLabels.get("ETH");
                etherLabel.setText(String.valueOf(pricesContainer.getEtherPrice()));

                var litecoinLabel = cryptoLabels.get("LTC");
                litecoinLabel.setText(String.valueOf(pricesContainer.getLitecoinPrice()));

                var bitcoinCashLabel = cryptoLabels.get("BCH");
                bitcoinCashLabel.setText(String.valueOf(pricesContainer.getBitcoinCashPrice()));

                var rippleLabel = cryptoLabels.get("XRP");
                rippleLabel.setText(String.valueOf(pricesContainer.getRipplePrice()));
              } finally {
                pricesContainer.getLock().unlock();
              }
            }
          }
        };

    addWindowResizeListener(primaryStage, background);

    animationTimer.start();

    priceUpdater.start();

    primaryStage.show();
  }

  private void addWindowResizeListener(Stage stage, Rectangle background) {
    ChangeListener<Number> stageSizeListener =
        ((observable, oldValue, newValue) -> {
          background.setHeight(stage.getHeight());
          background.setWidth(stage.getWidth());
        });
    stage.widthProperty().addListener(stageSizeListener);
    stage.heightProperty().addListener(stageSizeListener);
  }

  private Map<String, Label> createCryptoPriceLabels() {
    var bitcoinPrice = new Label("0");
    bitcoinPrice.setId("BTC");

    var etherPrice = new Label("0");
    etherPrice.setId("ETH");

    var liteCoinPrice = new Label("0");
    liteCoinPrice.setId("LTC");

    var bitcoinCashPrice = new Label("0");
    bitcoinCashPrice.setId("BCH");

    var ripplePrice = new Label("0");
    ripplePrice.setId("XRP");

    var cryptoLabelsMap = new HashMap<String, Label>();
    cryptoLabelsMap.put("BTC", bitcoinPrice);
    cryptoLabelsMap.put("ETH", etherPrice);
    cryptoLabelsMap.put("LTC", liteCoinPrice);
    cryptoLabelsMap.put("BCH", bitcoinCashPrice);
    cryptoLabelsMap.put("XRP", ripplePrice);

    return cryptoLabelsMap;
  }

  private GridPane createGrid() {
    var grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);
    return grid;
  }

  private void addLabelsToGrid(Map<String, Label> labels, GridPane grid) {
    var row = 0;
    for (Map.Entry<String, Label> entry : labels.entrySet()) {
      var cryptoName = entry.getKey();
      var nameLabel = new Label(cryptoName);
      nameLabel.setTextFill(Color.BLUE);
      nameLabel.setOnMousePressed(event -> nameLabel.setTextFill(Color.RED));
      nameLabel.setOnMouseReleased(event -> nameLabel.setTextFill(Color.BLUE));

      grid.add(nameLabel, 0, row);
      grid.add(entry.getValue(), 1, row);

      row++;
    }
  }

  private Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
    var backround = new Rectangle(width, height);
    var fillTransition =
        new FillTransition(Duration.millis(1000), backround, Color.LIGHTGREEN, Color.LIGHTBLUE);
    fillTransition.setCycleCount(Timeline.INDEFINITE);
    fillTransition.setAutoReverse(true);
    fillTransition.play();
    return backround;
  }

  @Override
  public void stop() {
    System.exit(0);
  }
}
