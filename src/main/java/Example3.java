import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
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
                        addAnimal(jsonObject, in);
                        break;
                    case 2:
                        findAnimal(jsonObject, in);
                        break;
                    case 3:
                        deleteAnimal(jsonObject, in);
                        break;
                    case 0:
                        System.out.println("Завершение программы");
                        break;
                    default:
                        System.out.println("Выберите корректную опцию");

                }
            }

            FileWriter file = new FileWriter("src/main/java/AnimalsJSON.json");
            file.write(jsonObject.toJSONString());
            file.close();
            System.out.println("JSON файл сохранен");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addAnimal(JSONObject jsonObject, Scanner in){
        JSONObject newAnimal = new JSONObject();
        System.out.println("Введите кличку животного");
        String name = in.nextLine();
        newAnimal.put("name", name);
        System.out.println("Введите тип животного");
        String type = in.nextLine();
        newAnimal.put("type", type);
        System.out.println("Введите возраст животного");
        String age = in.nextLine();
        newAnimal.put("age", age);

        JSONArray jArr = (JSONArray) jsonObject.get("animals");
        jArr.add(newAnimal);
    }

    public static void findAnimal(JSONObject jobj, Scanner in){

    }

    public static void deleteAnimal(JSONObject jobj, Scanner in){

    }
}