import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example2 {
    public static void main(String[] args) {
        try{
            File inputFile = new File("src/main/java/Animals.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("animal");

            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                System.out.println("\nТекущий элемент: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    System.out.println("Имя питомца: "
                    + element.getElementsByTagName("name").item(0)
                            .getTextContent());
                    System.out.println("Тип питомца: "
                            + element.getElementsByTagName("type").item(0)
                            .getTextContent());
                    System.out.println("Возраст: "
                            + element.getElementsByTagName("age").item(0)
                            .getTextContent());
                }
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
            doc.setXmlStandalone(true);
            doc.normalizeDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(src, result);
            System.out.println("XML документ сохранен");

        } catch (Exception pce){
            pce.printStackTrace();
        }
    }

    public static void addAnimal(Document doc, Scanner in){
        Element rootElement = doc.getDocumentElement();
        System.out.println("Введите имя животного:");
        String name = in.nextLine();
        System.out.println("Введите тип животного:");
        String type = in.nextLine();
        System.out.println("Введите возраст животного");
        String age = in.nextLine();

        Element animal = doc.createElement("animal");
        Element n = doc.createElement("name");
        n.appendChild(doc.createTextNode(name));
        animal.appendChild(n);
        Element t = doc.createElement("type");
        t.appendChild(doc.createTextNode(type));
        animal.appendChild(t);
        Element a = doc.createElement("age");
        a.appendChild(doc.createTextNode(age));
        animal.appendChild(a);

        rootElement.appendChild(animal);
    }

    public static void findAnimal(Document doc, Scanner in){
        System.out.println("Введите тип животного для поиска (кот, собака и тд):");
        String ty = in.nextLine();
        NodeList nodeList = doc.getElementsByTagName("animal");
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++){
            list.add(nodeList.item(i));
        }
        list = list.stream()
                .map(n -> (Element) n)
                .filter(element -> {
                    String type = element.getElementsByTagName("type").item(0).getTextContent();
                    return type.equalsIgnoreCase(ty);
                })
                .collect(Collectors.toList());
        if (list.isEmpty()){
            System.out.printf("Животных типа %s не найдено\n", ty);
            return;
        }
        System.out.println("Найдены животные:");
        for (Node n : list){
            Element e = (Element) n;
            String name = e.getElementsByTagName("name").item(0).getTextContent();
            String age = e.getElementsByTagName("age").item(0).getTextContent();
            System.out.printf("Кличка: %s, возраст: %s\n", name, age);
        }
    }

    public static void deleteAnimal(Document doc, Scanner in){
        System.out.println("Введите имя удаляемого животного");
        String del = in.nextLine();
        NodeList nodeList = doc.getElementsByTagName("animal");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node n = nodeList.item(i);
            Element e = (Element) n;
            String name = e.getElementsByTagName("name").item(0)
                    .getTextContent();
            if (del.equalsIgnoreCase(name)){
                Node parentNode = n.getParentNode();
                parentNode.removeChild(n);
                System.out.println("Животное удалено");
                return;
            }
        }
        System.out.println("Животное с таким именем не найдено");
    }
}

