package dal.model;

import java.util.ArrayList;

public class RequestList {
    private static ArrayList<Request> requests;
    private static RequestList instance = new RequestList();

    public RequestList() {
        requests = new ArrayList<>();
    }

    public static RequestList getInstance() {
        return instance;
    }

    public int getLength() {
        return requests.size();
    }

    public void appendRequest(Request request) {
        requests.add(request);
    }

    public Request getRequest(int index) {
        return requests.get(index);
    }

    public  void removeRequest(int index) {
        requests.remove(index);
    }
}
