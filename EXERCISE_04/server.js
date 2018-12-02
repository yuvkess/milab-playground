const http = require('http');
let server = http.createServer(function(request, response) {
  let url = (request.url == undefined) ? "" : request.url;
  response.writeHead(200);
  response.end(request.headers.host + url);
});

server.listen(8080);
