import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.println("Введите тип животного для поиска");
        String type = in.nextLine();
        JSONArray jArr = (JSONArray) jobj.get("animals");
        List<JSONObject> array = new ArrayList<>();
        for (Object o : jArr){
            array.add((JSONObject) o);
        }

        array = array.stream()
                .filter(animal -> {
                    String str = animal.get("type").toString();
                    return str.equalsIgnoreCase(type);
                })
                .collect(Collectors.toList());
        if (array.isEmpty()) {
            System.out.println("Животные не найдены");
        } else {
            array.forEach(animal -> {
                System.out.println("\nКличка: " + animal.get("name"));
                System.out.println("Возраст: " + animal.get("age"));
            });
        }
    }

    public static void deleteAnimal(JSONObject jobj, Scanner in){
        System.out.println("Введите имя удаляемого животного");
        String del = in.nextLine();
        JSONArray jArr = (JSONArray) jobj.get("animals");
        Iterator iterator = jArr.iterator();
        while (iterator.hasNext()){
            JSONObject jo = (JSONObject) iterator.next();
            String name = jo.get("name").toString();
            if (del.equalsIgnoreCase(name)){
                iterator.remove();
                System.out.println(name + " удален");
            }
        }
    }
}