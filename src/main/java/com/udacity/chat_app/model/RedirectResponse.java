package com.udacity.chat_app.model;

public class RedirectResponse {

    private String  status;

    private String redirect;

    

    public RedirectResponse(String status, String redirect) {
        this.status = status;
        this.redirect = redirect;
    }
    public RedirectResponse() {
        
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    

}