package dal.model;

import java.util.ArrayList;

public class RequestList {
    private static ArrayList<Request> requests;
    private int waitingRequests;
    private boolean isListChanged;
    private static RequestList instance = new RequestList();

    public RequestList() {
        requests = new ArrayList<>();
        waitingRequests = 0;
        isListChanged = false;
    }

    public static RequestList getInstance() {
        return instance;
    }

    public boolean isListChanged() {
        return isListChanged;
    }

    public void setListChanged(boolean listChanged) {
        isListChanged = listChanged;
    }

    public int getLength() {
        return requests.size();
    }

    public void appendRequest(Request request) {
        if(!requests.contains(request)) {
            requests.add(request);
            if(request.getStatus() == 0) { //0对应状态：用户初次请求
                waitingRequests++;
            }
        }
        else {
            //对于已在请求队列里的请求，检查与当前请求的状态更改
            if(request.getStatus() == -1) { //-1对应状态：用户取消请求
                this.removeRequest(request);
                waitingRequests--;
            }
            else if(request.getStatus() == 1 || request.getStatus() == -2) { //1对应状态：该请求已派工
                requests.set(requests.indexOf(request), request);                                       //-2对应状态：该请求被管理员拒绝
                waitingRequests--;
            }
            else if(this.getRequest(request).getStatus() != request.getStatus())
                requests.add(request);
        }
        if(!isListChanged) isListChanged = true;
    }

    public Request getRequest(int index) {
        if(index < 0 || index >= this.getLength()) return null;
        return requests.get(index);
    }

    public Request getRequest(Request request) {
        int index = requests.indexOf(request);
        return this.getRequest(index);
    }

    public Request getRequest(String rid) {
        Request request = new Request(rid);
        return this.getRequest(request);
    }

    public  void removeRequest(int index) {
        requests.remove(index);
        if(!isListChanged) isListChanged = true;
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        if(!isListChanged) isListChanged = true;
    }

    public int getWaitingRequests() {
        return waitingRequests;
    }

    public void setWaitingRequests(int waitingRequests) {
        this.waitingRequests = waitingRequests;
    }
}
