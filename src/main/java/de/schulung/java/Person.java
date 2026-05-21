package de.schulung.java;

import lombok.Getter;
import lombok.Setter;

@Getter(onMethod_ = @SayHello)
@Setter
public class Person {

  private String firstName;

  @SayHello("Guten Morgen!")
  public String sayHello() {
    return "Ich bin %s!".formatted(firstName);
  }

}
