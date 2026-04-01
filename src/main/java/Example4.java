import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.Scanner;

public class Example4 {
    static void main(String[] args) {
        String mainUrl = "https://urfu.ru/ru/news/";

        //Это попытка подключения к заведомо неисправной URL, чтобы продемонстрировать обработку ошибок
        //Document badConnection = tryConnect("https://BADURL.ru/ru/news/");

        Document mainPage = tryConnect(mainUrl);

        //Из-за динамической подгрузки новостей, нужно будет сформировать запрос к источнику новостей
        Element newsContainer = mainPage.selectFirst(".news-container");
        if (newsContainer == null) {
            System.out.println("Не найден контейнер новостей на главной странице.");
            return;
        }
        //Тут получаем параметры запроса из первого ответа
        String Url = newsContainer.attr("data-url");
        String rows = newsContainer.attr("data-rows");
        String cols = newsContainer.attr("data-cols");
        String categories = newsContainer.attr("data-categories");
        String pid = newsContainer.attr("data-pid");
        String offset = newsContainer.attr("data-offset");
        String categoriesSelected = newsContainer.attr("data-categories-selected");
        String fullMode = newsContainer.attr("data-full-mode");
        String page = newsContainer.attr("data-page");
        String showCategories = newsContainer.attr("data-show-categories");

        //URL для получения новостей
        String targetUrl = "https://urfu.ru" + Url + "?" +
                "rows=" + rows +
                "&cols=" + cols +
                "&categories=" + categories +
                "&pid=" + pid +
                "&offset=" + offset +
                "&categories-selected=" + categoriesSelected +
                "&full-mode=" + fullMode +
                "&page=" + page +
                "&show-categories=" + showCategories;


        //Загрузка и парсинг новостей
        Document newsFragment = tryConnect(targetUrl);

        Elements newsBlocks = newsFragment.select(".col.news-item.widget-news");
        if (newsBlocks.isEmpty()) {
            System.out.println("Новостей не найдено");
            return;
        }
        StringBuilder textForSave = new StringBuilder();
        System.out.println("Найдено новостей: " + newsBlocks.size() + "\n");
        for (Element block : newsBlocks) {
            Element dateElement = block.selectFirst(".date");
            String date = dateElement != null ? dateElement.text() : "Дата не указана";
            textForSave.append(date + "\t");

            Element titleLink = block.selectFirst(".snippet a");
            String title = titleLink != null ? titleLink.text() : "Заголовок не найден";
            textForSave.append(title + "\n\n");

            System.out.println("Заголовок: " + title);
            System.out.println("Дата: " + date);
            System.out.println("---");
        }
        save(textForSave.toString());


    }

    public static void save(String text){
        System.out.println("Записать новости в файл? y/n");
        Scanner in = new Scanner(System.in);
        char option = 'z';
        while (option != 'y' && option != 'n') {
            option = in.nextLine().charAt(0);
            switch (option) {
                case 'y':
                    try (FileWriter fw = new FileWriter("src/main/java/example4.txt")) {
                        fw.write(text);
                        System.out.println("Новости сохранены");
                    } catch (Exception e) {
                        System.out.println("Ошибка. Не удалось записать новости в файл");
                    }
                    break;
                case 'n':
                    break;
                default:
                    System.out.println("Введите корректную команду");
            }
        }
        in.close();
    }

    public static Document tryConnect(String url) {
        System.out.println("Попытка подключения к " + url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .get();
        } catch (Exception e) {
            System.out.println("Произошла ошибка подключения к сайту");
            doc = tryConnect(url, 1);
        }
        return doc;
    }

    public static Document tryConnect(String url, int i) {
        int counter = i;
        if (counter > 5) {
            System.out.println("Сайт недоступен, программа завершает свою работу");
            System.exit(0);
        }
        System.out.println("Повторная попытка подключения № " + counter);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .get();
        } catch (Exception e) {
            System.out.println("Произошла ошибка подключения к сайту");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
            doc = tryConnect(url, i + 1);
        }
        return doc;
    }
}