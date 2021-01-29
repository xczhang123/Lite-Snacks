package assignment2.backend;

import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSON {

    private JSONArray jsonArray;
    private List<Card> cards;

    public JSON() {
        JSONParser jsonParser = new JSONParser();
        String fileName = "src/main/resources/credit_cards.json";
        cards = new ArrayList<Card>();
        try (FileReader reader = new FileReader(fileName)) {
            // Read JSON file;
            Object obj = jsonParser.parse(reader);

            // JSON object to read fields
            JSONArray jsonArray = (JSONArray) obj;
            jsonArray.forEach(card -> parseCardObject((JSONObject) card));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Card validate(String name, String number) {
        boolean status = false;
        // JSONArray array = jsonObject.getJSONArray(0);
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(name)) {
                if (cards.get(i).getNum().equals(number)) {
                    status = true;
                    break;
                }
            }
        }
        if (status == true) {
            Card card = new Card(name, number);
            return card;
        } else {
            return null;
        }

    }

    private void parseCardObject(JSONObject card) {
        String name = (String) card.get("name");
        String num = (String) card.get("number");
        cards.add(new Card(name, num));
    }
}
