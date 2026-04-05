package timus;

import java.util.*;

public class timus_2002 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Map<String, String> users = new HashMap<>();
        Set<String> loggedUsers = new HashSet<>();

        int n = in.nextInt();
        in.nextLine();
        String[] strArray = new String[n];
        for (int i = 0; i < n; i++){
            strArray[i] = in.nextLine();
        }

        for (String str : strArray){
            String[] words = str.split(" ");
            String operation = words[0];
            String name = words[1];
            String password = null;
            if (words.length > 2){
                password = words[2];
            }
            switch (operation){
                case "register":
                    register(users, name, password);
                    break;
                case "login":
                    login(users, loggedUsers, name, password);
                    break;
                case "logout":
                    logout(users, loggedUsers, name);
                    break;
            }
        }
        in.close();

    }

    public static void register(Map<String, String> map, String name, String pass){
        if (!map.containsKey(name)){
            map.putIfAbsent(name, pass);
            System.out.println("success: new user added");
        } else {
            System.out.println("fail: user already exists");
        }
    }

    public static void login(Map<String, String> map, Set<String> set, String name, String pass){
        String userPassword = map.get(name);
        if (userPassword == null){
            System.out.println("fail: no such user");
        } else if (!userPassword.equals(pass)) {
            System.out.println("fail: incorrect password");
        } else if (set.contains(name)){
            System.out.println("fail: already logged in");
        } else {
            System.out.println("success: user logged in");
            set.add(name);
        }
    }

    public static void logout(Map<String, String> map, Set<String> set, String name){
        if (!map.containsKey(name)){
            System.out.println("fail: no such user");
        } else if (!set.contains(name)){
            System.out.println("fail: already logged out");
        } else {
            System.out.println("success: user logged out");
            set.remove(name);
        }
    }
}
