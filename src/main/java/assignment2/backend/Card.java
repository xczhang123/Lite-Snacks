package assignment2.backend;

public class Card {
  private String name;
  private String num;

  public Card(String name, String num){
    this.name = name;
    this.num = num;
  }

  public String getName(){
    return name;
  }

  public String getNum(){
    return num;
  }
}
