package com.myslyv4uk.client.impl;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myslyv4uk.client.api.ShowWeatherService;
import com.myslyv4uk.weather.api.WeatherService;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class ShowWeatherServiceImpl extends TimerTask implements ShowWeatherService {
  
  private int start;
  private int end;
  
  private static final Logger logger = LoggerFactory.getLogger(ShowWeatherServiceImpl.class);
  public volatile WeatherService weatherService;
  ScheduledExecutorService threadService; 
  //Timer timer;
  
  
  public ShowWeatherServiceImpl(WeatherService weatherService) {
    super();
    this.weatherService = weatherService;
  }
  
  public synchronized void transmitTemperature(int from, int to) {
    logger.info("" + weatherService.getCurrentTemperature(from, to));
  }
  
  public synchronized void start()  {
    logger.info("Service ShowWeatherServiceImpl activated");
    logger.info("Service ShowWeatherServiceImpl activated loggerinfo");
    setStart(20);
    setEnd(50);
//    timer = new Timer();
//    timer.schedule(this, 0, config.period());
    threadService = Executors.newScheduledThreadPool(1);
    threadService.scheduleAtFixedRate(this, 0, 3, TimeUnit.SECONDS);
  }
  
//  public synchronized void modified(Map<String, Object> configMap) throws IOException {
//    stop();
//    logger.info("Modified is invoked");
//    start(configMap);
//  }


  public synchronized void stop() {
    logger.info("Service ShowWeatherServiceImpl deactivated");
//    if(timer != null) {
//      timer.cancel();
//      logger.info("timer cancelled");
//      this.cancel();
//      this.
//      logger.info("timertask cancelled");
//    }
//    logger.info("maybe timer cancelled");
    if (threadService != null) {
      threadService.shutdownNow();
    }
  }
 
  @Override
  public void run() {
    transmitTemperature(start, end);
  }
}
