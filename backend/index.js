const express = require('express')
const cookieParser = require('cookie-parser')
const bodyParser = require('body-parser')
const app = express()
app.use(bodyParser.json())
const db = require('../Jokes_API/app/common/connect')


app.use(cookieParser())
function generateUserId() {
  const uuidv4 = require('uuid').v4;
  const userId = uuidv4();
  return userId;
}

// get jokes seen by user
app.get('/jokes/random', (req, res) => {
  const userId = req.cookies.userId == undefined ? generateUserId() : req.cookies.userId;

  const query = 'SELECT joke_id FROM votes WHERE user_id = ?';
  db.query(query, [userId], (err, results) => {
    if (err) {
      console.error(err);
      res.status(500).send('Internal server error');
      return;
    }

    // get jokes
    const seenJokeIds = results.map((result) => result.joke_id);
    let arrSeen = seenJokeIds.join(',');
    var subquery = '';
    if (seenJokeIds.length == 0) {
      subquery = 'SELECT id, joke FROM jokes ORDER BY RAND() LIMIT 1';
    } else {
      subquery = `SELECT id, joke FROM jokes WHERE id NOT IN (${arrSeen}) ORDER BY RAND() LIMIT 1`;
    }
    db.query(subquery, (err, results) => {
      if (err) {
        console.error(err);
        res.status(500).send('Internal server error');
        return;
      }

      if (results.length === 0) {
        res.cookie('userId', userId);
        res.send({ id: 0, joke: "That's all the jokes for today! Come back another day!" });
      }
      else{
        const joke = results[0];
        // set cookie
        res.cookie('userId', userId);
        res.cookie(`jokeId-${joke.id}`, 'seen');
        res.send({ id: joke.id, joke: joke.joke});
      }

    });
  });
});

// vote
app.post('/jokes/vote', (req, res) => {
  const jokeId = req.body.id;
  const voteType = req.body.type; // "like" or "dislike"
  const userId = req.cookies.userId;

  // update vote 
  const query = `UPDATE jokes SET ${voteType} = ${voteType} + 1 WHERE id = ?`;
  db.query(query, [jokeId], (err, results) => {
    if (err) {
      res.status(500).send('Internal server error');
      return;
    }

    // insert table votes
    const insertQuery = 'INSERT INTO votes (user_id, joke_id, vote_type) VALUES (?, ?, ?)';
    db.query(insertQuery, [userId, jokeId, voteType], (err, results) => {
      if (err) {
        res.status(500).send('Internal server error');
        return;
      }
      res.send({ messege: "Vote recorded" });
    });
  });
});


app.listen(4000, function () {
  console.log("Server listening on http://localhost:4000");
})
