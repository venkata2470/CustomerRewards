A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

·       Solve using Spring Boot

·       Create a RESTful endpoint



Request:

curl --location 'http://localhost:8080/purchase/calculateRewards' \
--header 'Content-Type: application/json' \
--data '{
  "purchases": [
    {
      "name": "John",
      "amount": 120,
      "month": "January"
    },
    {
      "name": "Welsh",
      "amount": 70,
      "month": "February"
    },
    {
      "name": "Gill",
      "amount": 120,
      "month": "March"
    },
    {
      "name": "Welsh",
      "amount": 120,
      "month": "March"
    },
    {
      "name": "Gill",
      "amount": 120,
      "month": "April"
    },
    {
      "name": "John",
      "amount": 70,
      "month": "February"
    }
  ]
}'



Response:


[
    {
        "name": "Gill",
        "customerPerMonthRewards": {
            "March": 90,
            "April": 90
        },
        "sumOfRewards": 180
    },
    {
        "name": "John",
        "customerPerMonthRewards": {
            "February": 20,
            "January": 90
        },
        "sumOfRewards": 110
    },
    {
        "name": "Welsh",
        "customerPerMonthRewards": {
            "March": 90,
            "February": 20
        },
        "sumOfRewards": 110
    }
]
