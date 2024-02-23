const jsonServer = require('json-server'),
  server = jsonServer.create(),
  router = jsonServer.router('db.json');

server.use(jsonServer.rewriter({
  '/api/auth/*': '/api/users',
  '/api/restaurants': '/api/restourants',
  '/api/restourants/:id/meals': '/api/meals'
}));

//server.use(jsonServer.bodyParser);
server.use((req, res, next) => {
  req.method = 'GET';
  next();
});

server.use('/api', router);
server.listen(3000, () => {
  console.log('JSON Server is running');
});
