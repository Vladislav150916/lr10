import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Example1_6 {
    public static void main(String[] args) {
        String mainUrl = "https://urfu.ru/ru/news/";
        try {
            // 1. Загружаем главную страницу, чтобы получить параметры
            Document mainPage = Jsoup.connect(mainUrl)
                    .userAgent("Mozilla/5.0")
                    .get();

            // 2. Находим контейнер с data-атрибутами
            Element newsContainer = mainPage.selectFirst(".news-container");
            if (newsContainer == null) {
                System.out.println("Не найден контейнер новостей на главной странице.");
                return;
            }

            // 3. Извлекаем параметры. Из-за динамической подгрузки новостей, нужно будет сформировать запрос к источнику новостей
            String apiUrl = newsContainer.attr("data-url"); // /get-news/ru/news/
            String rows = newsContainer.attr("data-rows");
            String cols = newsContainer.attr("data-cols");
            String categories = newsContainer.attr("data-categories");
            String pid = newsContainer.attr("data-pid");
            String offset = newsContainer.attr("data-offset");
            String categoriesSelected = newsContainer.attr("data-categories-selected");
            String fullMode = newsContainer.attr("data-full-mode");
            String page = newsContainer.attr("data-page");
            String showCategories = newsContainer.attr("data-show-categories");

            // 4. Формируем полный URL для API
            String apiFullUrl = "https://urfu.ru" + apiUrl + "?" +
                    "rows=" + rows +
                    "&cols=" + cols +
                    "&categories=" + categories +
                    "&pid=" + pid +
                    "&offset=" + offset +
                    "&categories-selected=" + categoriesSelected +
                    "&full-mode=" + fullMode +
                    "&page=" + page +
                    "&show-categories=" + showCategories;

            System.out.println("Запрашиваем API: " + apiFullUrl);

            // 5. Загружаем фрагмент с новостями
            Document newsFragment = Jsoup.connect(apiFullUrl)
                    .userAgent("Mozilla/5.0")
                    .get();

            // 6. Парсим новости из фрагмента
            Elements newsBlocks = newsFragment.select(".col.news-item.widget-news");
            if (newsBlocks.isEmpty()) {
                System.out.println("Новостей не найдено в ответе API.");
                return;
            }

            System.out.println("Найдено новостей: " + newsBlocks.size() + "\n");
            for (Element block : newsBlocks) {
                // Дата
                Element dateElement = block.selectFirst(".date");
                String date = dateElement != null ? dateElement.text() : "Дата не указана";

                // Заголовок (ссылка внутри .snippet)
                Element titleLink = block.selectFirst(".snippet a");
                String title = titleLink != null ? titleLink.text() : "Заголовок не найден";

                System.out.println("Заголовок: " + title);
                System.out.println("Дата: " + date);
                System.out.println("---");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}