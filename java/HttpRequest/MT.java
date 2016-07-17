package http;


import okhttp3.MediaType;


public class MT
{
    public static final MediaType MEDIA_TYPE_DEFUALT = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
}
