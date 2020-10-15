Requires 'curl' on your unix environment.

Getting started:

bash$ curl http://localhost:8080/foobar/ping

Should answer with '{"message": "Hi!"}'

Fake some business process:

bash$ curl  -d '{"department":"sales", "processKey":"customer-lost"}' -H "Content-Type: application/json" -X POST http://localhost:8080/foobar/do-business 

or

bash$ curl  -d '{"department":"procurement", "processKey":"order"}' -H "Content-Type: application/json" -X POST http://localhost:8080/foobar/do-business 

Depending on the payload you will receive a http 200 or 500 response. In case of any error the message is returned.