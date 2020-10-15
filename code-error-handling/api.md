Requires 'curl' on your unix environment.

Getting started:

bash$ curl http://localhost:8080/foobar/ping

Should answer with '{"message": "Hi!"}'

Send an invoice:

bash$ curl  -d '{"to":"customer@foobar.org"}' -H "Content-Type: application/json" -X POST http://localhost:8080/acme/send-invoice 

You should receive a http 200 response