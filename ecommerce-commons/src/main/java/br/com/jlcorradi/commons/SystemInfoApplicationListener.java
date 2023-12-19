package br.com.jlcorradi.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class SystemInfoApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>
{
  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event)
  {
    String maxHeapSize = getRuntimeMaxHeapSize();
    String port = event.getEnvironment().getProperty("server.port");

    log.info("Max Heap Size (-Xmx): {}", maxHeapSize);
    log.info("Server Port: {}", port);

    String[] activeProfiles = event.getEnvironment().getActiveProfiles();
    log.info("Active Profiles: {}", String.join(",", activeProfiles));
    log.info("Datasource Connection: {}", event.getEnvironment().getProperty("spring.datasource.url"));
    log.info("Connection Pool: {}", event.getEnvironment().getProperty("spring.datasource.hikari.pool-name"));
    log.info("Connection Pool size: {}", event.getEnvironment().getProperty("spring.datasource.hikari.maximum-pool-size"));
  }

  private String getRuntimeMaxHeapSize()
  {
    long maxMemory = Runtime.getRuntime().maxMemory();
    double maxMemoryInMegabytes = maxMemory / (1024.0 * 1024);
    return String.format("%.2f MB", maxMemoryInMegabytes);
  }
}
