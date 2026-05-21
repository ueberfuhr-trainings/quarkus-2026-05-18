package de.schulung.java;

public class AnnotationDemo {

  public static void main(String[] args) throws Exception {

    System.out.println("Los geht's!");

    final var framework = new SayHelloFramework();
    final var person = new Person().setFirstName("John");

    framework.handle(person);

  }

}
