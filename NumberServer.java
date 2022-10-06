import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> strarr = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else if (url.getPath().contains("/add")) {
            System.out.println("the Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("count")) {
                num += Integer.parseInt(parameters[1]);
                return String.format("Number increased by %s! It's now %d", parameters[1], num);
            } else if (parameters[0].equals("s")) {
                strarr.add(parameters[1]);
                return String.format("Added %s", parameters[1]);
            }
            return "404 Not Found!";
        } else if (url.getPath().contains("/search")) {
            System.out.println("the Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")) {
                for(String str : strarr) {
                    System.out.println(str);
                    if(str.contains(parameters[1])) {
                        return String.format("%s", str);
                    }
                }
            }
            System.out.println(parameters[1]);
            return "No Search Results Found!";
        }
        return "we have failed";
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
