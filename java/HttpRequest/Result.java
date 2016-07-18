package httprequest;


public class Result
{
    private int code;

    private String message = "";

    private String body = "";

    public boolean isSucceed()
    {
        return code >= 200 && code < 300;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
