package com.szymon.demo.logging;

import com.szymon.demo.collections.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LogManager {

    public static void add(HttpServletRequest request, String message) {
        List<Log> logs = (ArrayList<Log>) request.getAttribute("LOGS");

        logs.add(new Log(message));

        request.setAttribute("LOGS", logs);
    }

}
