const express = require('express')
const app = express()
let port = process.env.PORT || 3000

app.get('/', (req, res) => {
    res.send("Success!")
})

app.get("*", (req, res) => {
    res.send("Error! The page you are looking for does not exist.")
});

app.listen(port, () => {
    console.log(`App is listening on http://localhost:${port}`)
});