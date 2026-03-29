import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Scanner;

public class Example3 {
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser
                    .parse(new FileReader("src/main/java/AnimalsJSON.json"));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println("Корневой элемент: "
                    + jsonObject.keySet().iterator().next());
            JSONArray jsonArray = (JSONArray) jsonObject.get("animals");

            for (Object o : jsonArray){
                JSONObject animal = (JSONObject) o;
                System.out.println("\nТекущий элемент: animal");
                System.out.println("Кличка: " + animal.get("name"));
                System.out.println("Тип: " + animal.get("type"));
                System.out.println("Возраст: " + animal.get("age"));
            }

            Scanner in = new Scanner(System.in);
            int option = 999;

            while (option != 0) {
                System.out.println("\nВыберите опцию:\n1. Добавить животное в лист\n2. Найти животное\n3. Удалить животное\n0. Конец программы");
                option = in.nextInt();
                in.nextLine();
                switch (option){
                    case 1:
                        addAnimal(doc, in);
                        break;
                    case 2:
                        findAnimal(doc, in);
                        break;
                    case 3:
                        deleteAnimal(doc, in);
                        break;
                    case 0:
                        System.out.println("Завершение программы");
                        break;
                    default:
                        System.out.println("Выберите корректную опцию");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addAnimal(){

    }

    public static void findAnimal(){

    }

    public static void deleteAnimal(){

    }
}