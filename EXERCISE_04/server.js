const http = require('http');
let server = http.createServer(function(request, response) {
  response.writeHead(200);
  response.end(request.headers.host+request.url);
});

server.listen(8080);
