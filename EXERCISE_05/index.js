const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');

let app = express();
app.use(bodyParser.json());

app.get('/files/:name', (req,res) =>{
	let name = req.params['name'];

	fs.exists(`./files/${name}`, (exists) =>{
		if(exists){
			let readStream = fs.createReadStream(`./files/${name}`);
			readStream.pipe(res);
		} else {
			res.writeHead(400);
			res.end(`The file "${name}" does not exist`);
		}
	})
})

app.listen(3000, () => {
	console.log('Listening on port 3000');
})