const express = require('express');
const fs = require('fs');
const app = express();

app.get('/files/:fileName', (req, res) => {
 let name = req.params['fileName'];
 fs.exists(`./files/${name}`, (exists) => {
 	if (exists) {
 		const readStream = fs.createReadStream(`./files/${name}`);
 		readStream.pipe(res);
 	} else {
 		res.writeHead(404);
 		res.end(`The file "${name}" does not exist`);
 	}
 });
});


app.listen(3000, () => {
 console.log('Listening on port 3000!');
});