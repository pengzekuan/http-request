**API Request methods**

### driver 

- `URLRequest.class`
- `HTTPClientRequest.class`

### Usage

```
Request request = RequestFactory.instance(URLRequest.class);
Response response = request.get(url);
String data = response.getData();
```

### methods

- `request(String uri, String method, Map<String, Object> query, Object params, Map<String, String> headers)`

- `get(String url)`

- `get(String url, Map<String, Object> query)`

- `get(String url, Map<String, Object> query, Map<String, String> header)`

- `post(String uri)`

- `post(String uri, Map<String, Object> query)`

- `post(String uri, Map<String, Object> query, Object params)`
    
- `post(String uri, Map<String, Object> query, Object params, Map<String, String> header)`