package de.schulung.java;

public class SayHelloFramework {

  public void handle(Object object) throws Exception {
    // suche nach Methoden mit @SayHello
    final var methods = object.getClass().getDeclaredMethods();
    for (var method : methods) {
      var sayHello = method.getAnnotation(SayHello.class);
      if (sayHello != null) {
        var prefix = sayHello.value();
        // jede Methode: rufe auf und verwende Rückgabewert
        var returnValue = method.invoke(object);
        // gebe Rückgabewert in einer Hallo-Nachricht aus
        var message = "%s %s".formatted(prefix, returnValue);
        System.out.println(message);
      }
    }
  }

}
