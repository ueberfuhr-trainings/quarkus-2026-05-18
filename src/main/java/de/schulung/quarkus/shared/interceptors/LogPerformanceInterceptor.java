package de.schulung.quarkus.shared.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

@Interceptor
@LogPerformance
@Slf4j
public class LogPerformanceInterceptor {

  @AroundInvoke
  public Object logPerformance(InvocationContext context) throws Exception {
    var ts1 = System.currentTimeMillis();
    try {
      return context.proceed(); // Weiterleitung an das Originalobjekt
    } finally {
      var ts2 = System.currentTimeMillis();
      log.info(
        "Methode {} aufgerufen, Dauer: {} ms",
        context.getMethod().getName(),
        ts2 - ts1
      );
    }
  }

}
