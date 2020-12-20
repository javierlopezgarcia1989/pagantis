
javi = db.users.insertOne({ 
  "email": "javi@gmail.com",
  "lastname": "Lopez",
  "name": "Javier",
  "password": "1234"
});

marcos = db.users.insertOne({ 
  "email": "marcus@gmail.com",
  "lastname": "Muñoz",
  "name": "Marcos",
  "password": "1234"
});

db.wallets.insertOne({ 
  "userId": marcos.insertedId.valueOf(),
  "money": "900.0",
  "name": "Cartera A",
});

db.wallets.insertOne({ 
  "userId": marcos.insertedId.valueOf(),
  "money": "0.0",
  "name": "Cartera B",
});

db.wallets.insertOne({ 
  "userId": marcos.insertedId.valueOf(),
  "money": "1150.0",
  "name": "Cartera C",
});

db.wallets.insertOne({ 
  "userId": marcos.insertedId.valueOf(),
  "money": "777.0",
  "name": "Cartera D",
});


db.wallets.insertOne({ 
  "userId": javi.insertedId.valueOf(),
  "money": "900.0",
  "name": "Cartera M",
});

db.wallets.insertOne({ 
  "userId": javi.insertedId.valueOf(),
  "money": "0.0",
  "name": "Cartera N",
});

db.wallets.insertOne({ 
  "userId": javi.insertedId.valueOf(),
  "money": "1150.0",
  "name": "Cartera O",
});

db.wallets.insertOne({ 
  "userId": javi.insertedId.valueOf(),
  "money": "777.0",
  "name": "Cartera P",
});