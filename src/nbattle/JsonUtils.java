package nbattle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonUtils {
    private static final String HASH = "BN45CJLI149XCdp434xhzzzLKxCKnBP1432lkU";

    public static String parseUrl(String sUrl, String sParams) {
        // Создаем URL из строки.
        URL url = JsonUtils.createUrl(sUrl);
        if (url == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            String params = "hash=" + HASH + sParams;

            // Send post request
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();
            // done

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            // Построчно считываем результат в объект StringBuilder.
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                System.out.println(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    // Парсим некоторые данные об играх.
    public static JSONArray parseListJson(String resultJson) {
        try {
            // Конвертируем строку с Json в JSONObject для дальнейшего его парсинга.
            JSONObject gamesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);

            // Получаем состояние текущих игр.
            System.out.println("Состояние сервера: " + gamesJsonObject.get("msg"));

            // Получаем массив элементов для поля.
            /* {"msg":"ok","games":[{"id":"1","host":"vasya"},{"id":"2","host":"loh"},{"id":"3","host":"123"},{"id":"4","host":"1"}]} */
            if (gamesJsonObject.get("info") == null) {
                return (JSONArray) gamesJsonObject.get("games");
            }

            System.out.println("Сообщение: " + gamesJsonObject.get("info"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Парсим некоторые данные создании игры.
    public static boolean parseCreateJson(String resultJson) {
        try {
            // Конвертируем строку с Json в JSONObject для дальнейшего его парсинга.
            JSONObject gamesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);

            // Получаем состояние текущих игр.
            System.out.println("Состояние сервера: " + gamesJsonObject.get("msg"));

            if (gamesJsonObject.get("info") == null) {
                System.out.println("ID игры: " + gamesJsonObject.get("id"));
                return true;
            }

            System.out.println("Сообщение: " + gamesJsonObject.get("info"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean parseConnectJson(String resultJson) {
        try {
            JSONObject gamesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);

            System.out.println("Состояние сервера: " + gamesJsonObject.get("msg"));

            if (gamesJsonObject.get("info") == null) {
                System.out.println("Кто ходит? " + gamesJsonObject.get("mover"));
                return true;
            }

            System.out.println("Ошибка: " + gamesJsonObject.get("info"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean parseStatusJson(String resultJson) {
        try {
            JSONObject gamesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);

            System.out.println("Состояние сервера: " + gamesJsonObject.get("msg"));

            if (gamesJsonObject.get("info") == null) {
                System.out.println("Кто таков? " + gamesJsonObject.get("player"));
                System.out.println("Кто ходит? " + gamesJsonObject.get("mover"));
                return true;
            }

            System.out.println("Ошибка: " + gamesJsonObject.get("info"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean parseDeleteJson(String resultJson) {
        try {
            JSONObject gamesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);

            System.out.println("Состояние сервера: " + gamesJsonObject.get("msg"));

            if (gamesJsonObject.get("info") == null) {
                System.out.println("Всё ОК!");
                return true;
            }

            System.out.println("Ошибка: " + gamesJsonObject.get("info"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Создаем объект URL из указанной в параметре строки.
    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}