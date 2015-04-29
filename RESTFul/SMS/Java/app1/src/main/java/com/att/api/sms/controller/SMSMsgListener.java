package com.att.api.sms.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.att.api.controller.APIController;
import com.att.api.sms.model.SMSReceiveMessage;
import com.att.api.sms.service.SMSFileUtil;

public class SMSMsgListener extends APIController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, 
            HttpServletResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader bReader = request.getReader();
            String line;
            while ((line = bReader.readLine()) != null) {
                sb.append(line);
            }

            final String contentBody = sb.toString();
            JSONObject jobj = new JSONObject(contentBody);
            SMSReceiveMessage msg = SMSReceiveMessage.valueOf(jobj);

            final int limit = Integer.parseInt(appConfig.getProperty("limit"));
            SMSFileUtil.addSMSMsg(msg, limit);

        } catch (Exception e) {
            // log error
            e.printStackTrace();
        }
    }     

    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) {
        // Accept only POST requests
        return;
    }
}
